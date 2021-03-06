package org.urumov.messengersystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.urumov.messengersystem.domain.dto.DepartmentDto;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.dto.error.ErrorResponse;
import org.urumov.messengersystem.domain.dto.error.ValidationErrorResponse;
import org.urumov.messengersystem.domain.entity.Department;
import org.urumov.messengersystem.service.DepartmentService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "Departments REST API operations")
@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('USER','MANAGER', 'ADMIN')")
public class DepartmentController {

    private final DepartmentService departmentService;


    @Operation(
            summary = "Get department by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Department for requested ID", content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DepartmentDto department(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @Operation(
            summary = "Get department by name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Department for requested name", content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DepartmentDto department(@PathVariable String name) {
        return departmentService.getByName(name);
    }

    @Operation(
            summary = "Get all departments",
            responses = @ApiResponse(responseCode = "200",
                    description = "Departments",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentDto.class))))
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DepartmentDto> departments() {
        return departmentService.findAll();
    }


    @Operation(
            summary = "Get department manager by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Manager from department for requested ID",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/{id}/manager", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto manager(@PathVariable Long id) {
        return departmentService.getDepartmentManager(id);
    }

    @Operation(
            summary = "Get department manager by name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Manager from department for requested name",
                            content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/{name}/managerbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto manager(@PathVariable String name) {
        return departmentService.getDepartmentManager(name);
    }


    @Operation(
            summary = "Set manager to department",
            responses = @ApiResponse(responseCode = "200", description = "Manager set to department")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{departmentId}/manager/{userId}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public void setManagerToDepartment(@PathVariable Integer departmentId, @PathVariable Integer userId) {
        departmentService.setManagerForDepartment(departmentId, userId);
    }

    @Operation(
            summary = "Remove manager from department",
            responses = @ApiResponse(responseCode = "204", description = "manager is removed from department")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{departmentId}/manager")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public void removeManagerFromDepartment(@PathVariable Integer departmentId) {
        departmentService.removeManagerFromDepartment(departmentId);
    }


    @Operation(
            summary = "Get all users from departments",
            responses = @ApiResponse(responseCode = "200",
                    description = "Departments users",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class))))
    )
    @GetMapping(value = "/{id}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> departmentUsers(@PathVariable Long id) {
        return departmentService.findUsersFromDepartment(id);
    }


    @Operation(
            summary = "Create new department",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New department is created"),
                    @ApiResponse(responseCode = "400", description = "Wrong request format",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<Void> createDepartment(@Valid @RequestBody DepartmentDto request) {
        final Department department = departmentService.save(request);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(department.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(
            summary = "Update existing department",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Department for requested ID is updated",
                            content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
                    @ApiResponse(responseCode = "400", description = "Wrong request format",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public void updateDepartment(@Valid @RequestBody DepartmentDto request) {
        departmentService.update(request);
    }

    @Operation(
            summary = "Add user to department",
            responses = @ApiResponse(responseCode = "204", description = "User added to department")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{departmentId}/{userId}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public void addUserToDepartment(@PathVariable Integer departmentId, @PathVariable Integer userId) {
        departmentService.addUserToDepartment(departmentId, userId);
    }

    @Operation(
            summary = "Remove user from department",
            responses = @ApiResponse(responseCode = "204", description = "User is removed from department")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{departmentId}/{userId}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public void removeUserFromDepartment(@PathVariable Integer departmentId, @PathVariable Integer userId) {
        departmentService.removeUserFromDepartment(departmentId, userId);
    }


    @Operation(
            summary = "Remove department by ID",
            responses = @ApiResponse(responseCode = "204", description = "Department for requested ID is removed")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteById(id);
    }
}

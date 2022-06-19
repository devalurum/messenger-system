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
import org.urumov.messengersystem.domain.dto.BuildingSchemeDto;
import org.urumov.messengersystem.domain.dto.error.ErrorResponse;
import org.urumov.messengersystem.domain.dto.error.ValidationErrorResponse;
import org.urumov.messengersystem.domain.entity.BuildingScheme;
import org.urumov.messengersystem.service.BuildingSchemeService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "Building scheme REST API operations")
@RestController
@RequestMapping("/api/v1/schemes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('USER','MANAGER', 'ADMIN')")
public class BuildingSchemeController {

    private final BuildingSchemeService buildingSchemeService;

    @Operation(
            summary = "Get scheme by name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Scheme for requested name", content = @Content(schema = @Schema(implementation = BuildingSchemeDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BuildingSchemeDto scheme(@PathVariable String name) {
        return buildingSchemeService.getByName(name);
    }

    @Operation(
            summary = "Get all schemes build",
            responses = @ApiResponse(responseCode = "200",
                    description = "Build schemes",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BuildingSchemeDto.class))))
    )
    @GetMapping(value = "/channels/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BuildingSchemeDto> schemes() {
        return buildingSchemeService.getAll();
    }

    @Operation(
            summary = "Create new scheme",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New scheme is created"),
                    @ApiResponse(responseCode = "400", description = "Wrong request format", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<Void> createScheme(@Valid @RequestBody BuildingSchemeDto request) {
        final BuildingScheme buildingScheme = buildingSchemeService.save(request);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(buildingScheme.getName())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(
            summary = "Update existing scheme",
            responses = {
                    @ApiResponse(responseCode = "200", description = "scheme for requested name is updated", content = @Content(schema = @Schema(implementation = BuildingSchemeDto.class))),
                    @ApiResponse(responseCode = "400", description = "Wrong request format", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public void updateScheme(@Valid @RequestBody BuildingSchemeDto request) {
        buildingSchemeService.update(request);
    }

    @Operation(
            summary = "Remove scheme by name",
            responses = @ApiResponse(responseCode = "204", description = "Scheme for requested name is removed")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public void deleteUser(@PathVariable String name) {
        buildingSchemeService.delete(name);
    }

}

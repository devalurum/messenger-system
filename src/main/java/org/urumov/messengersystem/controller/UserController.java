package org.urumov.messengersystem.controller;

import com.google.maps.model.LatLng;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.model.Role;
import org.urumov.messengersystem.domain.model.User;
import org.urumov.messengersystem.domain.dto.error.ErrorResponse;
import org.urumov.messengersystem.domain.dto.error.ValidationErrorResponse;
import org.urumov.messengersystem.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "Users REST API operations")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Get all users",
            responses = @ApiResponse(responseCode = "200",
                    description = "Users",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class))))
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> users() {
        return userService.findAll();
    }


    @Operation(
            summary = "Get user by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User for requested ID", content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto user(@PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(
            summary = "Get user by email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User for requested email", content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto user(@PathVariable String email) {
        return userService.getByEmail(email);
    }


    @Operation(
            summary = "Create new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New user is created"),
                    @ApiResponse(responseCode = "400", description = "Wrong request format", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto request) {
        final User user = userService.create(request);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(
            summary = "Update existing user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User for requested ID is updated", content = @Content(schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Wrong request format", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@Valid @RequestBody UserDto request) {
        userService.update(request);
    }


    @Operation(
            summary = "Get location user by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Location for requested ID", content = @Content(schema = @Schema(implementation = LatLng.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/{id}/location/", produces = MediaType.APPLICATION_JSON_VALUE)
    public LatLng userLocation(@PathVariable Long id) {
        return userService.getLocation(id);
    }

    @Operation(
            summary = "Update location user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Location for requested ID is updated"),
                    @ApiResponse(responseCode = "400", description = "Wrong request format", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping("/location")
    public void updateLocationUser(@RequestParam("id") Long id,
                                   @RequestParam("latitude") double latitude,
                                   @RequestParam("longitude") double longitude) {
        userService.updateLocation(id, latitude, longitude);
    }

    @Operation(
            summary = "Update location user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Location for requested ID is updated", content = @Content(schema = @Schema(implementation = LatLng.class))),
                    @ApiResponse(responseCode = "400", description = "Wrong request format", content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping("/{id}/location/")
    public void updateLocationUser(@PathVariable Long id,
                                   @Valid @RequestBody LatLng latLng) {
        userService.updateLocation(id, latLng);
    }


    @Operation(
            summary = "Remove user by ID",
            responses = @ApiResponse(responseCode = "204", description = "User for requested ID is removed")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }


}

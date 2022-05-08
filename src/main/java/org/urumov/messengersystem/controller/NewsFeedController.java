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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.urumov.messengersystem.dto.ItemFeedDto;
import org.urumov.messengersystem.entities.ItemFeed;
import org.urumov.messengersystem.entities.NewsFeed;
import org.urumov.messengersystem.model.error.ErrorResponse;
import org.urumov.messengersystem.model.error.ValidationErrorResponse;
import org.urumov.messengersystem.service.NewsFeedService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "News Feed REST API operations")
@RestController
@RequestMapping("/api/v1/newsfeed")
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    @Operation(
            summary = "Get all item news feed",
            responses = @ApiResponse(responseCode = "200",
                    description = "News feed",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemFeedDto.class))))
    )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemFeedDto> newsFeed(@PathVariable Long id) {
        return newsFeedService.getItemsFeed(id);
    }

    @Operation(
            summary = "Get item news feed by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item news feed for requested ID",
                            content = @Content(schema = @Schema(implementation = ItemFeedDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/{id}/item/{idItem}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemFeedDto itemNewsFeed(@PathVariable Long id, @PathVariable Long idItem) {
        return newsFeedService.getItemFeed(id, idItem);
    }

    @Operation(
            summary = "Create new item feed",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New item feed is created"),
                    @ApiResponse(responseCode = "400", description = "Wrong request format",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @PostMapping(value = "/{id}/item/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createItem(@PathVariable Long id, @Valid @RequestBody ItemFeedDto request) {
        final ItemFeed itemFeed = newsFeedService.createItem(id, request);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(itemFeed.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(
            summary = "Create news feed",
            responses = {
                    @ApiResponse(responseCode = "201", description = "News feed is created"),
                    @ApiResponse(responseCode = "409", description = "News feed already is created", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Wrong request format",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNewsFeed() {
        final NewsFeed newsFeed = newsFeedService.createNewsFeed();
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newsFeed.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }


    @Operation(
            summary = "Remove item from News Feed by IDs",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Item for requested IDs is removed"),
                    @ApiResponse(responseCode = "404", description = "Item feed or News feed not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}/item/{idItem}")
    public void deleteItem(@PathVariable Long id, @PathVariable Long idItem) {
        newsFeedService.deleteItem(id, idItem);
    }

    @Operation(
            summary = "Remove all items from News Feed by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Items for requested ID is removed from News feed"),
                    @ApiResponse(responseCode = "404", description = "News feed not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteItems(@PathVariable Long id) {
        newsFeedService.deleteAllItems(id);
    }

}

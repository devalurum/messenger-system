package org.urumov.messengersystem.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.urumov.messengersystem.domain.dto.ChannelDto;
import org.urumov.messengersystem.domain.dto.MessageDto;
import org.urumov.messengersystem.domain.dto.error.ErrorResponse;
import org.urumov.messengersystem.domain.dto.error.ValidationErrorResponse;
import org.urumov.messengersystem.domain.model.Channel;
import org.urumov.messengersystem.domain.model.Message;
import org.urumov.messengersystem.domain.model.Role;
import org.urumov.messengersystem.domain.model.User;
import org.urumov.messengersystem.security.CurrentUser;
import org.urumov.messengersystem.service.ChatService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "Chat REST API operations")
@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(
            summary = "Get channel by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "channel for requested ID",
                            content = @Content(schema = @Schema(implementation = ChannelDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/channel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ChannelDto channel(@PathVariable Long id) {
        return chatService.getChannelById(id);
    }

    @Operation(
            summary = "Create new channel",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New channel is created"),
                    @ApiResponse(responseCode = "400", description = "Wrong request format",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @PostMapping(value = "/channel", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createChannel(@Parameter(hidden = true) @CurrentUser User user, @Valid @RequestBody ChannelDto request) {
        final Channel channel = chatService.createChannel(request, user);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(channel.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(
            summary = "Update existing channel",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Channel for requested ID is updated",
                            content = @Content(schema = @Schema(implementation = ChannelDto.class))),
                    @ApiResponse(responseCode = "400", description = "Wrong request format",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping(value = "/channel", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateChannel(@Valid @RequestBody ChannelDto request) {
        chatService.updateChannel(request);
    }

    @Operation(
            summary = "Remove channel by ID",
            responses = @ApiResponse(responseCode = "204", description = "Channel for requested ID is removed")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/channel/{id}")
    public void deleteChannel(@PathVariable Long id) {
        chatService.deleteChannelById(id);
    }

    @Operation(
            summary = "Get all messages from channel",
            responses = @ApiResponse(responseCode = "200",
                    description = "Messages from channel",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MessageDto.class))))
    )
    @GetMapping(value = "/channel/{id}/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDto> messagesFromChannel(@PathVariable Long id) {
        return chatService.getMessagesFromChannel(id);
    }

    @Operation(
            summary = "Send message to channel",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New message is sent"),
                    @ApiResponse(responseCode = "400", description = "Wrong request format",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @PostMapping(value = "/channel/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendMessageToChannel(@PathVariable Long id,
                                                     @Parameter(hidden = true) @CurrentUser User user,
                                                     @RequestBody MessageDto request) {
        chatService.addMessageToChannel(id, user.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Get message from channel by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "message from channel for requested ID",
                            content = @Content(schema = @Schema(implementation = MessageDto.class))),
                    @ApiResponse(responseCode = "404", description = "Requested data not found",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping(value = "/channel/{id}/{idMessage}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto channel(@PathVariable Long id, @PathVariable Long idMessage) {
        return chatService.getMessageFromChannel(id, idMessage);
    }


    @Operation(
            summary = "Remove message from channel by ID",
            responses = @ApiResponse(responseCode = "204", description = "Message from channel for requested ID is removed")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/channel/{id}/{idMessage}")
    public void deleteMessageFromChannel(@PathVariable Long id, @PathVariable Long idMessage) {
        chatService.deleteMessageFromChannel(id, idMessage);
    }

    @Operation(
            summary = "Remove all messages from channel by ID",
            responses = @ApiResponse(responseCode = "204", description = "Messages from channel for requested ID is removed")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/channel/{idChannel}")
    public void deleteAllMessageFromChannel(@PathVariable Long idChannel) {
        chatService.deleteAllMessagesFromChannel(idChannel);
    }

    @Operation(
            summary = "Add user to channel",
            responses = @ApiResponse(responseCode = "204", description = "User added to channel")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/channel/{id}/{userId}")
    public void addUserToChannel(@PathVariable Long id, @PathVariable Long userId) {
        chatService.addUserToChannel(id, userId);
    }

    @Operation(
            summary = "Remove user from channel",
            responses = @ApiResponse(responseCode = "204", description = "User is removed from channel")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/channel/{id}/{userId}")
    public void removeUserFromChannel(@PathVariable Long id, @PathVariable Long userId) {
        chatService.removeUserFromChannel(id, userId);
    }


    @Operation(
            summary = "Get all messages from personality chat",
            responses = @ApiResponse(responseCode = "200",
                    description = "Messages from chat",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MessageDto.class))))
    )
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDto> messagesFromPersonalityChat(@Parameter(hidden = true) @CurrentUser User user,
                                                        @RequestParam("receiverId") Long receiverId) {
        return chatService.getMessagesFromPersonalityChat(user.getId(), receiverId);
    }

    @Operation(
            summary = "Send message in personality chat",
            responses = {
                    @ApiResponse(responseCode = "201", description = "New message is sent"),
                    @ApiResponse(responseCode = "400", description = "Wrong request format",
                            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
            }
    )
    @PostMapping(value = "/{idReceiver}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendMessage(@Parameter(hidden = true) @CurrentUser User user,
                                            @PathVariable Long idReceiver,
                                            @RequestBody MessageDto request) {

        final Message message = chatService.saveMessage(user.getId(), idReceiver, request);

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(message.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(
            summary = "Remove messages from personality chat by ID",
            responses = @ApiResponse(responseCode = "204",
                    description = "Messages from personality chat for requested ID is removed")
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        chatService.deleteMessage(id);
    }
}

package org.urumov.messengersystem.service;

import org.urumov.messengersystem.domain.dto.ChannelDto;
import org.urumov.messengersystem.domain.dto.MessageDto;
import org.urumov.messengersystem.domain.model.Channel;
import org.urumov.messengersystem.domain.model.Message;
import org.urumov.messengersystem.domain.model.User;

import java.util.List;

public interface ChatService {

    ChannelDto getChannelById(long id);

    Channel createChannel(ChannelDto channelDto, User creator);

    Channel updateChannel(ChannelDto channelDto);

    boolean deleteChannelById(long id);

    List<MessageDto> getMessagesFromChannel(long id);

    boolean addMessageToChannel(long id, long senderId, MessageDto messageDto);

    MessageDto getMessageFromChannel(long channelId, long messageId);

    boolean deleteMessageFromChannel(long channelId, long messageId);

    boolean deleteAllMessagesFromChannel(long id);

    void addUserToChannel(long channelId, long userId);

    void removeUserFromChannel(long channelId, long userId);

    List<MessageDto> getMessagesFromPersonalityChat(long senderId, long receiverId);

    Message saveMessage(long senderId, long receiverId,MessageDto messageDto);

    boolean deleteMessage(long id);

}

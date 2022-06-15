package org.urumov.messengersystem.service;

import org.springframework.transaction.annotation.Transactional;
import org.urumov.messengersystem.domain.dto.ChannelDto;
import org.urumov.messengersystem.domain.dto.MessageDto;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.entity.Channel;
import org.urumov.messengersystem.domain.entity.Message;
import org.urumov.messengersystem.domain.entity.User;

import java.util.List;

public interface ChatService {

    ChannelDto getChannelById(long id);

    List<ChannelDto> getAllChannels();

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

    List<UserDto> getAllPersonalitiesChats(long id);

    List<MessageDto> getMessagesFromPersonalityChat(long senderId, long receiverId);

    Message saveMessage(long senderId, long receiverId,MessageDto messageDto);

    boolean deleteMessage(long id);

}

package org.urumov.messengersystem.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.urumov.messengersystem.domain.dto.ChannelDto;
import org.urumov.messengersystem.domain.dto.MessageDto;
import org.urumov.messengersystem.domain.model.Channel;
import org.urumov.messengersystem.domain.model.Message;
import org.urumov.messengersystem.domain.model.User;
import org.urumov.messengersystem.domain.mapper.ChannelMapper;
import org.urumov.messengersystem.domain.mapper.MessageMapper;
import org.urumov.messengersystem.repository.ChannelRepository;
import org.urumov.messengersystem.repository.MessageRepository;
import org.urumov.messengersystem.repository.UserRepository;
import org.urumov.messengersystem.service.ChatService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelMapper channelMapper;
    private final MessageMapper messageMapper;

    @Override
    @Transactional
    public ChannelDto getChannelById(long id) {
        return channelRepository.findById(id)
                .map(channelMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    @Transactional
    public Channel createChannel(@NonNull ChannelDto channelDto) {
        Channel channel = channelMapper.toModel(channelDto);
        return channelRepository.save(channel);
    }

    @Override
    @Transactional
    public Channel updateChannel(@NonNull ChannelDto channelDto) {
        return channelRepository.findById(channelDto.getId())
                .map(channel -> {
                    channelMapper.updateModel(channelDto, channel);
                    channelRepository.save(channel);
                    return channel;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean deleteChannelById(long id) {
        return channelRepository.findById(id)
                .map(channel -> {
                    channelRepository.deleteById(channel.getId());
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    @Transactional
    public List<MessageDto> getMessagesFromChannel(long id) {
        return channelRepository.findById(id)
                .map(channel -> channel.getMessages().stream()
                        .map(messageMapper::toDto)
                        .collect(Collectors.toList()))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean addMessageToChannel(long id, long senderId, MessageDto messageDto) {
        return channelRepository.findById(id)
                .map(channel -> {
                    Message message = messageMapper.toModel(messageDto);
                    User sender = userRepository.findById(senderId).orElseThrow(EntityNotFoundException::new);
                    message.setSender(sender);
                    channel.getMessages().add(message);
                    channelRepository.save(channel);
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public MessageDto getMessageFromChannel(long channelId, long messageId) {
        return messageRepository.findByChannel_IdAndId(channelId, messageId)
                .map(messageMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    @Transactional
    public boolean deleteMessageFromChannel(long channelId, long messageId) {
        return channelRepository.findById(channelId)
                .map(channel -> {
                    channel.getMessages().stream()
                            .filter(message -> message.getId().equals(messageId))
                            .findFirst()
                            .map(p -> {
                                channel.getMessages().remove(p);
                                return p;
                            });
                    channelRepository.save(channel);
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean deleteAllMessagesFromChannel(long id) {
        return channelRepository.findById(id)
                .map(channel -> {
                    channel.setMessages(null);
                    channelRepository.save(channel);
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void addUserToChannel(long channelId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        channelRepository.findById(channelId)
                .map(ch -> {
                    ch.getUsers().add(user);
                    return ch;
                })
                .orElseThrow(EntityNotFoundException::new);

    }

    @Override
    @Transactional
    public void removeUserFromChannel(long channelId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);

        channelRepository.findById(channelId)
                .map(ch -> {
                    ch.getUsers().remove(user);
                    return ch;
                })
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    @Transactional
    public List<MessageDto> getMessagesFromPersonalityChat(long senderId, long receiverId) {
        return messageRepository.findMessagesBetweenTwoUsers(senderId, receiverId)
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Message saveMessage(long senderId, long receiverId, MessageDto messageDto) {
        Message message = messageMapper.toModel(messageDto);
        User sender = userRepository.findById(senderId)
                .orElseThrow(EntityNotFoundException::new);
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(EntityNotFoundException::new);
        message.setSender(sender);
        message.setReceiver(receiver);
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public boolean deleteMessage(long id) {
        return messageRepository.findById(id)
                .map(channel -> {
                    messageRepository.deleteById(channel.getId());
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

}

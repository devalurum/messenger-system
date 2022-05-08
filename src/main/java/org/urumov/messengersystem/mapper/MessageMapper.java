package org.urumov.messengersystem.mapper;

import org.mapstruct.Mapper;
import org.urumov.messengersystem.dto.MessageDto;
import org.urumov.messengersystem.entities.Message;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto toDto(Message message);

    Message toModel(MessageDto message);
}

package org.urumov.messengersystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.urumov.messengersystem.domain.dto.MessageDto;
import org.urumov.messengersystem.domain.model.Message;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MessageMapper {

    MessageDto toDto(Message message);

    @Mapping(target = "time", ignore = true)
    Message toModel(MessageDto message);
}

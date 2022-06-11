package org.urumov.messengersystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.urumov.messengersystem.domain.dto.MessageDto;
import org.urumov.messengersystem.domain.entity.Message;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MessageMapper {

    MessageDto toDto(Message message);

    @Mapping(target = "time", ignore = true)
    @Mapping(target = "id", source = "id", ignore = true)
    Message toModel(MessageDto message);

    void updateModel(MessageDto messageDto,
                     @MappingTarget Message message);
}

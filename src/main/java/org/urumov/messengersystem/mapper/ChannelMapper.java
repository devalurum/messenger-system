package org.urumov.messengersystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.urumov.messengersystem.dto.ChannelDto;
import org.urumov.messengersystem.entities.Channel;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    ChannelDto toDto(Channel channelDto);

    Channel toModel(ChannelDto channelDto);

    void updateModel(ChannelDto channelDto, @MappingTarget Channel channel);
}

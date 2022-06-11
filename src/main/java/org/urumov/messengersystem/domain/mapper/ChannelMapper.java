package org.urumov.messengersystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.urumov.messengersystem.domain.dto.ChannelDto;
import org.urumov.messengersystem.domain.entity.Channel;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ChannelMapper {

    ChannelDto toDto(Channel channelDto);

    @Mapping(target = "id", source = "id", ignore = true)
    Channel toModel(ChannelDto channelDto);

    void updateModel(ChannelDto channelDto, @MappingTarget Channel channel);
}

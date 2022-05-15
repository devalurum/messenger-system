package org.urumov.messengersystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.urumov.messengersystem.domain.dto.ItemFeedDto;
import org.urumov.messengersystem.domain.model.ItemFeed;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ItemFeedMapper {

    ItemFeedDto toDto(ItemFeed itemFeed);

    @Mapping(target = "time", ignore = true)
    ItemFeed toModel(ItemFeedDto itemFeedDto);
}

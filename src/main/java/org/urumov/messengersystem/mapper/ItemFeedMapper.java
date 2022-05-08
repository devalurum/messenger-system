package org.urumov.messengersystem.mapper;

import org.mapstruct.Mapper;
import org.urumov.messengersystem.dto.ItemFeedDto;
import org.urumov.messengersystem.entities.ItemFeed;

@Mapper(componentModel = "spring")
public interface ItemFeedMapper {

    ItemFeedDto toDto(ItemFeed itemFeed);

    ItemFeed toModel(ItemFeedDto itemFeedDto);
}

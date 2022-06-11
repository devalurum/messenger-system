package org.urumov.messengersystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.urumov.messengersystem.domain.dto.ItemFeedDto;
import org.urumov.messengersystem.domain.entity.ItemFeed;

@Mapper(componentModel = "spring")
public abstract class ItemFeedMapper {

    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "senderId", source = "sender.id")
    public abstract ItemFeedDto toDto(ItemFeed itemFeed);

    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(target = "time", ignore = true)
    public abstract ItemFeed toModel(ItemFeedDto itemFeedDto);

}

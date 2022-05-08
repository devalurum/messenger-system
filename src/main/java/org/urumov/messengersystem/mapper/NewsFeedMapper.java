package org.urumov.messengersystem.mapper;

import org.mapstruct.Mapper;
import org.urumov.messengersystem.dto.NewsFeedDto;
import org.urumov.messengersystem.entities.NewsFeed;

@Mapper(componentModel = "spring")
public interface NewsFeedMapper {

    NewsFeedDto toDto(NewsFeed newsFeed);

    NewsFeed toModel(NewsFeedDto newsFeedDto);
}

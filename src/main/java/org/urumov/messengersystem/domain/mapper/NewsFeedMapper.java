package org.urumov.messengersystem.domain.mapper;

import org.mapstruct.Mapper;
import org.urumov.messengersystem.domain.dto.NewsFeedDto;
import org.urumov.messengersystem.domain.model.NewsFeed;

@Mapper(componentModel = "spring")
public interface NewsFeedMapper {

    NewsFeedDto toDto(NewsFeed newsFeed);

    NewsFeed toModel(NewsFeedDto newsFeedDto);
}

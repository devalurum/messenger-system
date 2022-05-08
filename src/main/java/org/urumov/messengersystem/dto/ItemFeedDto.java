package org.urumov.messengersystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.urumov.messengersystem.dto.DepartmentDto;
import org.urumov.messengersystem.dto.NewsFeedDto;
import org.urumov.messengersystem.dto.UserDto;

import java.io.Serializable;

@Data
@Jacksonized
@Builder
public class ItemFeedDto implements Serializable {
    private final Long id;
    private final String message;
    private final UserDto sender;
    private final DepartmentDto department;
    private final NewsFeedDto newsFeed;
}

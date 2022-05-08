package org.urumov.messengersystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Jacksonized
@Builder
public class ChannelDto implements Serializable {
    private final Long id;
    private final String name;
    private final LocalDateTime createdTime;
    private final UserDto creator;
}

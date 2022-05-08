package org.urumov.messengersystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Jacksonized
@Builder
public class MessageDto implements Serializable {
    private final int id;
    private final LocalDateTime time;
    private final String message;
}

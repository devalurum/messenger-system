package org.urumov.messengersystem.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Jacksonized
@Builder
public class MessageDto implements Serializable {
    private final int id;

    private final LocalDateTime time;

    @NotBlank
    private final String message;
}

package org.urumov.messengersystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.List;

@Data
@Jacksonized
@Builder
public class NewsFeedDto implements Serializable {
    private final Long id;
}

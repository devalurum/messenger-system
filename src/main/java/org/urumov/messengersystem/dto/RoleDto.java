package org.urumov.messengersystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Jacksonized
@Builder
public class RoleDto implements Serializable {
    private final Long id;
    private final String name;
}

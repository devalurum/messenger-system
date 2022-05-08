package org.urumov.messengersystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Jacksonized
@Builder
public class BuildingSchemeDto implements Serializable {
    private final String name;
    private final String geoJson;
}

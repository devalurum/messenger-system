package org.urumov.messengersystem.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Jacksonized
@Builder
public class BuildingSchemeDto implements Serializable {

    @NotBlank
    @Size(min = 3, max = 255)
    private final String name;

    @NotBlank
    private final String geoJson;
}

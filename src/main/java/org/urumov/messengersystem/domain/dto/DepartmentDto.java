package org.urumov.messengersystem.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Jacksonized
@Builder
public class DepartmentDto implements Serializable {

    private final Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private final String name;

}

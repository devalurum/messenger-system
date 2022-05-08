package org.urumov.messengersystem.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.urumov.messengersystem.model.Gender;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Jacksonized
@Builder
public class UserDto implements Serializable {
    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final LocalDate dob;
    private final String phone;
    private final String image;
    //private final List<RoleDto> roles;

    /*@JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private final Point coordinates;*/
}

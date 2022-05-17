package org.urumov.messengersystem.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.urumov.messengersystem.domain.model.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@Jacksonized
@Builder
public class UserDto implements Serializable {

    private final Long id;

    @NotBlank
    @Size(min = 3, max = 254)
    @Email(message = "Email should be valid")
    private final String username;

    @NotBlank
    @Size(min = 3, max = 40)
    private final String password;

    @NotBlank
    @Size(min = 2, max = 50)
    private final String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private final String lastName;
    private final Gender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private final LocalDate dob;

    private final String phone;
    private final Set<String> roles;

    /*@JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private final Point coordinates;*/
}

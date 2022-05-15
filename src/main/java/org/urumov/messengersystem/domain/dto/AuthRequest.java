package org.urumov.messengersystem.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Jacksonized
@Builder
public class AuthRequest {
    @NotNull
    @Email
    @Size(min = 3, max = 254)
    @Email(message = "Email should be valid")
    private String username;

    @NotNull
    @Size(min = 3, max = 40)
    private String password;
}


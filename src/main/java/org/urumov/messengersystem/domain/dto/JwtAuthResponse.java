package org.urumov.messengersystem.domain.dto;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
public class JwtAuthResponse {
    private String token;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String token) {
        this.token = token;
    }
}

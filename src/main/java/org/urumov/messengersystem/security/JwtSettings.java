package org.urumov.messengersystem.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConstructorBinding
@ConfigurationProperties("security.jwt")
@Validated
@RequiredArgsConstructor
@Getter
public class JwtSettings {

    @NotNull
    private final String iis;

    @NotNull
    private final String secret;

    @NotNull
    private final RSAPublicKey publicKey;

    @NotNull
    private final RSAPrivateKey privateKey;

    private final long tokenExpiredSeconds;

}

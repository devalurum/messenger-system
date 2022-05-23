package org.urumov.messengersystem.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.urumov.messengersystem.domain.model.User;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@Slf4j
public class SecurityAuditorAware {

    @Bean
    public AuditorAware<User> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = null;
            if (authentication != null && authentication.getPrincipal() instanceof User) {
                user = (User) authentication.getPrincipal();
            }
            return Optional.ofNullable(user);
        };
    }
}

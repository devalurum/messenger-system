package org.urumov.messengersystem.domain.model;

import lombok.experimental.FieldNameConstants;
import org.springframework.security.core.GrantedAuthority;


@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum Role implements GrantedAuthority {
    @FieldNameConstants.Include USER,
    @FieldNameConstants.Include MANAGER,
    @FieldNameConstants.Include ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

    @Override
    public String toString() {
        return name();
    }
}

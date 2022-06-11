package org.urumov.messengersystem.domain.mapper;

import org.mapstruct.*;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.entity.Role;
import org.urumov.messengersystem.domain.entity.User;

import java.util.Collections;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "authorities", target = "roles")
    public abstract UserDto toDTO(User user);

    @InheritInverseConfiguration
    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "id", source = "id", ignore = true)
    @Mapping(source = "roles", target = "authorities", qualifiedByName = "stringToRole")
    public abstract User toModel(UserDto dto);

    @Mapping(source = "roles", target = "authorities", qualifiedByName = "stringToRole")
    public abstract void updateModel(UserDto userDto, @MappingTarget User user);

    @Named("stringToRole")
    protected Set<Role> stringToRole(Set<String> authorities) {
        if (authorities != null) {
            return authorities.stream()
                    .map(Role::valueOf)
                    .collect(toSet());
        }
        return Collections.emptySet();
    }
}

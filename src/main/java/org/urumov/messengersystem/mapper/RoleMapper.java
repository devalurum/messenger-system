package org.urumov.messengersystem.mapper;

import org.mapstruct.Mapper;
import org.urumov.messengersystem.dto.RoleDto;
import org.urumov.messengersystem.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
}

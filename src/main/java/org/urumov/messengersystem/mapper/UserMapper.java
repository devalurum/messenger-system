package org.urumov.messengersystem.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.urumov.messengersystem.dto.UserDto;
import org.urumov.messengersystem.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserDto toDTO(User user);

    @InheritInverseConfiguration
    User toModel(UserDto dto);

    void updateModel(UserDto userDto, @MappingTarget User user);

}

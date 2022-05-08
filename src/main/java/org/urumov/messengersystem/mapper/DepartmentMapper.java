package org.urumov.messengersystem.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.urumov.messengersystem.dto.DepartmentDto;
import org.urumov.messengersystem.entities.Department;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class})
public interface DepartmentMapper {

    DepartmentDto toDto(Department department);

    @InheritInverseConfiguration
    Department toModel(DepartmentDto dto);

    void updateModel(DepartmentDto departmentDto, @MappingTarget Department user);
}

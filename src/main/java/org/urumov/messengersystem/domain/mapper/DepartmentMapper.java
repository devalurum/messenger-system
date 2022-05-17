package org.urumov.messengersystem.domain.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.urumov.messengersystem.domain.dto.DepartmentDto;
import org.urumov.messengersystem.domain.model.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentDto toDto(Department department);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id", ignore = true)
    Department toModel(DepartmentDto dto);

    void updateModel(DepartmentDto departmentDto, @MappingTarget Department user);
}

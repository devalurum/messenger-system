package org.urumov.messengersystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.urumov.messengersystem.dto.BuildingSchemeDto;
import org.urumov.messengersystem.entities.BuildingScheme;

@Mapper(componentModel = "spring")
public interface BuildingSchemeMapper {

    BuildingSchemeDto toDTO(BuildingScheme buildingScheme);

    BuildingScheme toModel(BuildingSchemeDto buildingSchemeDto);

    void updateModel(BuildingSchemeDto buildingSchemeDto,
                     @MappingTarget BuildingScheme buildingScheme);


}

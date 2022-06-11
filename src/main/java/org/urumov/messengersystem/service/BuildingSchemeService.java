package org.urumov.messengersystem.service;

import org.urumov.messengersystem.domain.dto.BuildingSchemeDto;
import org.urumov.messengersystem.domain.entity.BuildingScheme;

public interface BuildingSchemeService {

    BuildingSchemeDto getByName(String name);

    BuildingScheme save(BuildingSchemeDto buildingSchemeDto);

    BuildingScheme update(BuildingSchemeDto buildingSchemeDto);

    boolean delete(String name);
}

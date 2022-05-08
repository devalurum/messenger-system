package org.urumov.messengersystem.service;

import org.urumov.messengersystem.dto.BuildingSchemeDto;
import org.urumov.messengersystem.entities.BuildingScheme;

public interface BuildingSchemeService {

    BuildingSchemeDto getByName(String name);

    BuildingScheme save(BuildingSchemeDto buildingSchemeDto);

    BuildingScheme update(BuildingSchemeDto buildingSchemeDto);

    boolean delete(String name);
}

package org.urumov.messengersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urumov.messengersystem.domain.entity.BuildingScheme;

public interface BuildingSchemeRepository extends JpaRepository<BuildingScheme, String> {

}
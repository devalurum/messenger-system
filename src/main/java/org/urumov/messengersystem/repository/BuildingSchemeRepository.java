package org.urumov.messengersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urumov.messengersystem.entities.BuildingScheme;

import java.util.Optional;

public interface BuildingSchemeRepository extends JpaRepository<BuildingScheme, String> {

}
package org.urumov.messengersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urumov.messengersystem.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
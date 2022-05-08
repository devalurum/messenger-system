package org.urumov.messengersystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urumov.messengersystem.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findDepartmentByName(String name);
}
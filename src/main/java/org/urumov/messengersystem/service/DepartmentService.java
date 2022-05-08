package org.urumov.messengersystem.service;

import org.urumov.messengersystem.dto.DepartmentDto;
import org.urumov.messengersystem.dto.UserDto;
import org.urumov.messengersystem.entities.Department;

import javax.transaction.Transactional;
import java.util.List;

public interface DepartmentService {

    DepartmentDto getById(long id);

    DepartmentDto getByName(String name);

    List<DepartmentDto> findAll();

    Department save(DepartmentDto departmentDto);

    Department update(DepartmentDto departmentDto);

    UserDto getDepartmentManager(long id);

    UserDto getDepartmentManager(String name);

    void addUserToDepartment(long departmentId, long userId);

    void removeUserFromDepartment(long departmentId, long userId);

    boolean deleteById(long id);

    List<UserDto> findUsersFromDepartment(long id);
}

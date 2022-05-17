package org.urumov.messengersystem.service;

import org.urumov.messengersystem.domain.dto.DepartmentDto;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.model.Department;
import org.urumov.messengersystem.domain.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface DepartmentService {

    DepartmentDto getById(long id);

    DepartmentDto getByName(String name);

    List<DepartmentDto> findAll();

    Department save(DepartmentDto departmentDto);

    Department update(DepartmentDto departmentDto);

    UserDto getDepartmentManager(long id);

    User setManagerForDepartment(long departmentId, long userId);

    void removeManagerFromDepartment(long departmentId);

    UserDto getDepartmentManager(String name);

    void addUserToDepartment(long departmentId, long userId);

    void removeUserFromDepartment(long departmentId, long userId);

    boolean deleteById(long id);

    List<UserDto> findUsersFromDepartment(long id);
}

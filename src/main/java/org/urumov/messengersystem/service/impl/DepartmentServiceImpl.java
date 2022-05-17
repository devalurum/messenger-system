package org.urumov.messengersystem.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.urumov.messengersystem.domain.dto.DepartmentDto;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.mapper.DepartmentMapper;
import org.urumov.messengersystem.domain.mapper.UserMapper;
import org.urumov.messengersystem.domain.model.Department;
import org.urumov.messengersystem.domain.model.User;
import org.urumov.messengersystem.repository.DepartmentRepository;
import org.urumov.messengersystem.repository.UserRepository;
import org.urumov.messengersystem.service.DepartmentService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final DepartmentMapper departmentMapper;


    @Override
    @Transactional
    public DepartmentDto getById(long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public DepartmentDto getByName(String name) {
        return departmentRepository.findDepartmentByName(name)
                .map(departmentMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Department save(@NonNull DepartmentDto departmentDto) {
        Department department = departmentMapper.toModel(departmentDto);
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public Department update(@NonNull DepartmentDto departmentDto) {
        return departmentRepository.findById(departmentDto.getId())
                .map(scheme -> {
                    departmentMapper.updateModel(departmentDto, scheme);
                    departmentRepository.save(scheme);
                    return scheme;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public UserDto getDepartmentManager(long id) {
        return departmentRepository.findById(id)
                .map(department -> userMapper.toDTO(department.getAdmin()))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public User setManagerForDepartment(long departmentId, long userId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(EntityNotFoundException::new);

        return userRepository.findById(userId)
                .map(user -> {
                    user.setDepartment(department);
                    department.setAdmin(user);
                    departmentRepository.save(department);
                    return userRepository.save(user);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void removeManagerFromDepartment(long departmentId) {
        departmentRepository.findById(departmentId)
                .map(depart -> {
                    depart.setAdmin(null);
                    return departmentRepository.save(depart);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public UserDto getDepartmentManager(String name) {
        return departmentRepository.findDepartmentByName(name)
                .map(department -> userMapper.toDTO(department.getAdmin()))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void addUserToDepartment(long departmentId, long userId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(EntityNotFoundException::new);

        userRepository.findById(userId)
                .map(user -> {
                    user.setDepartment(department);
                    return userRepository.save(user);
                })
                .orElseThrow(EntityNotFoundException::new);

    }

    @Override
    @Transactional
    public void removeUserFromDepartment(long departmentId, long userId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(EntityNotFoundException::new);

        userRepository.findById(userId)
                .filter(user -> user.getDepartment().equals(department))
                .map(user -> {
                    user.setDepartment(null);
                    return userRepository.save(user);
                })
                .orElseThrow(EntityNotFoundException::new);

    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        return departmentRepository.findById(id)
                .map(department -> {
                    departmentRepository.deleteById(department.getId());
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public List<UserDto> findUsersFromDepartment(long id) {
        return departmentRepository.findById(id)
                .map(department -> department.getUsers().stream()
                        .map(userMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(EntityNotFoundException::new);
    }

}

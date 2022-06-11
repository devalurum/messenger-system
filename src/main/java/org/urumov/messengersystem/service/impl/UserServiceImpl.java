package org.urumov.messengersystem.service.impl;

import com.google.maps.model.LatLng;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.entity.User;
import org.urumov.messengersystem.domain.mapper.UserMapper;
import org.urumov.messengersystem.repository.UserRepository;
import org.urumov.messengersystem.service.UserService;
import org.urumov.messengersystem.utils.PointReader;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PointReader pointReader;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User create(@NonNull UserDto userDto) {
        if (userRepository.findUserByUsername(userDto.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }

        User user = userMapper.toModel(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDto getById(long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public UserDto getByEmail(String email) {
        return userRepository.findUserByUsername(email)
                .map(userMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public User update(@NonNull UserDto userDto) {
        return userRepository.findById(userDto.getId())
                .map(user -> {
                    userMapper.updateModel(userDto, user);
                    return userRepository.save(user);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public User updateLocation(long id, double latitude, double longitude) {
        return userRepository.findById(id)
                .map(user -> {
                    Point point = pointReader.createPointFromLatLon(latitude, longitude);
                    user.setCoordinates(point);
                    return userRepository.save(user);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public User updateLocation(long id, @NonNull LatLng latLng) {
        return userRepository.findById(id)
                .map(user -> {
                    Point point = pointReader.createPointFromLatLon(latLng);
                    user.setCoordinates(point);
                    return userRepository.save(user);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public LatLng getLocation(long id) {
        return userRepository.findById(id)
                .map(user -> pointReader.convertPointToLatLng(user.getCoordinates()))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(user.getId());
                    return true;
                })
                .orElseThrow(EntityNotFoundException::new);
    }
}

package org.urumov.messengersystem.service;

import com.google.maps.model.LatLng;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    User create(UserDto userDto);

    UserDto getById(long id);

    UserDto getByEmail(String email);

    User update(UserDto userDto);

    User updateLocation(long id, double latitude, double longitude);

    User updateLocation(long id, LatLng latLng);

    LatLng getLocation(long id);

    boolean deleteById(long id);
}

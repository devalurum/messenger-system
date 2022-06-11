package org.urumov.messengersystem.service;

import org.urumov.messengersystem.domain.dto.AuthRequest;
import org.urumov.messengersystem.domain.dto.JwtAuthResponse;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.entity.User;

public interface AuthService {

    JwtAuthResponse authenticateUser(AuthRequest authRequest);

    User registerUser(UserDto userDto);
}

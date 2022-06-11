package org.urumov.messengersystem.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.urumov.messengersystem.security.JwtTokenProvider;
import org.urumov.messengersystem.domain.dto.AuthRequest;
import org.urumov.messengersystem.domain.dto.JwtAuthResponse;
import org.urumov.messengersystem.domain.dto.UserDto;
import org.urumov.messengersystem.domain.mapper.UserMapper;
import org.urumov.messengersystem.domain.entity.User;
import org.urumov.messengersystem.repository.UserRepository;
import org.urumov.messengersystem.service.AuthService;

import javax.validation.ValidationException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final UserMapper userMapper;


    @Override
    @Transactional
    public JwtAuthResponse authenticateUser(@NonNull AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        log.info("User with [email: {}] has logged in", user.getUsername());

        return new JwtAuthResponse(jwt);
    }

    @Override
    @Transactional
    public User registerUser(@NonNull UserDto userDto) {
        if (userRepository.findUserByUsername(userDto.getUsername()).isPresent()) {
            throw new ValidationException("Username already exists!");
        }

        User user = userMapper.toModel(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);

        return userRepository.save(user);
    }

}

package com.shop.polyedrum.service.impl;

import com.shop.polyedrum.dao.UserRepository;
import com.shop.polyedrum.domain.Role;
import com.shop.polyedrum.domain.User;
import com.shop.polyedrum.dto.AuthResponse;
import com.shop.polyedrum.dto.UserDTO;
import com.shop.polyedrum.exception.ApiException;
import com.shop.polyedrum.security.JwtService;
import com.shop.polyedrum.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new ApiException("email is already exists!", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse
                .builder()
                .token(token)
                .mail(user.getEmail())
                .message("user successfully registered")
                .build();
    }

    @Override
    public AuthResponse authenticate(UserDTO userDTO) {

        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        var user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow();

        String token = jwtService.generateToken(user);

        return AuthResponse
                .builder()
                .token(token)
                .mail(user.getEmail())
                .message("user successfully login")
                .build();
    }

}

package com.shop.polyedrum.service;

import com.shop.polyedrum.dto.AuthResponse;
import com.shop.polyedrum.dto.UserDTO;

public interface AuthService {
    AuthResponse register(UserDTO userDTO);
    AuthResponse authenticate(UserDTO userDTO);
}
package com.shop.polyedrum.controller;

import com.shop.polyedrum.dto.ResponseHandler;
import com.shop.polyedrum.dto.UserDTO;
import com.shop.polyedrum.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping(value = {"/register", "/signUp"})
    public ResponseEntity<Object> register(@RequestBody UserDTO userDTO){
        return ResponseHandler
                .responseBuilder("", HttpStatus.CREATED, authService.register(userDTO));
    }

    @PostMapping(value = {"/login", "signIn"})
    public ResponseEntity<Object> login(@RequestBody UserDTO userDTO){
        return ResponseHandler
                .responseBuilder("", HttpStatus.OK, authService.authenticate(userDTO));
    }
}

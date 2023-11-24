package com.codergy.userservice.ScalerImplementation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codergy.userservice.ScalerImplementation.dtos.LoginRequestDto;
import com.codergy.userservice.ScalerImplementation.dtos.UserDto;
import com.codergy.userservice.ScalerImplementation.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        super();
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request)
    {
        return authService.login(request.getEmail(), request.getPassword());
    }
    @PostMapping("/logout")
    @PostMapping("/signin")
    @PostMapping("/validate")

    
}

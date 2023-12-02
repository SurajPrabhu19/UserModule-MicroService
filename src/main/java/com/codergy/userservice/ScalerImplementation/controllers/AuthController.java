package com.codergy.userservice.ScalerImplementation.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codergy.userservice.ScalerImplementation.dtos.LoginRequestDto;
import com.codergy.userservice.ScalerImplementation.dtos.LogoutRequestDto;
import com.codergy.userservice.ScalerImplementation.dtos.SignUpRequestDto;
import com.codergy.userservice.ScalerImplementation.dtos.UserDto;
import com.codergy.userservice.ScalerImplementation.dtos.ValidateRequestDto;
import com.codergy.userservice.ScalerImplementation.models.SessionStatus;
import com.codergy.userservice.ScalerImplementation.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController{

    private AuthService authService;

    public AuthController(AuthService authService) {
        super();
        this.authService = authService;
    }

    // @Override
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) throws Exception
    {
        return authService.login(request.getEmail(), request.getPassword());
    }
    // @Override
    @PostMapping("/logout")
    public ResponseEntity<UserDto> logout(@RequestBody LogoutRequestDto request) {
        return authService.logout(request.getToken(), request.getUserId());
    }

    // @Override
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request) {
        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // @Override
    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateRequestDto request)
    {

        return new ResponseEntity<SessionStatus>(authService.validate(request.getToken(), request.getUserId()), HttpStatus.OK);
    }

}

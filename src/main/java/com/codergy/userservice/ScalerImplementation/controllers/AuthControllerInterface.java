package com.codergy.userservice.ScalerImplementation.controllers;

import org.springframework.http.ResponseEntity;

import com.codergy.userservice.ScalerImplementation.dtos.LoginRequestDto;
import com.codergy.userservice.ScalerImplementation.dtos.LogoutRequestDto;
import com.codergy.userservice.ScalerImplementation.dtos.SignUpRequestDto;
import com.codergy.userservice.ScalerImplementation.dtos.UserDto;
import com.codergy.userservice.ScalerImplementation.dtos.ValidateRequestDto;

public interface AuthControllerInterface {

    ResponseEntity<UserDto> login(LoginRequestDto request);

    ResponseEntity<UserDto> logout(LogoutRequestDto request);

    ResponseEntity<UserDto> signUp(SignUpRequestDto request);

    ResponseEntity<UserDto> validate(ValidateRequestDto request);

}
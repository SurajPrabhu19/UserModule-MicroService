package com.codergy.userservice.services;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.codergy.userservice.dtos.UserRequestDto;
import com.codergy.userservice.dtos.UserResponseDto;

public interface UserService {

    UserResponseDto signUpUser(UserRequestDto userDetails);

    HttpStatusCode loginUser(UserRequestDto userDetails) throws Exception;

    List<UserResponseDto> getAllUsers();

    UserResponseDto logoutUser(UUID id) throws Exception;



}

package com.codergy.userservice.dtos;

import java.util.ArrayList;

import com.codergy.userservice.models.Session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String email;
    private String password;
}

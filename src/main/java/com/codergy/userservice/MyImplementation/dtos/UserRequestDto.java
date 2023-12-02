package com.codergy.userservice.dtos;

import com.codergy.userservice.models.Session;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String email;
    private String password;
    // private Session session;
}

package com.codergy.userservice.ScalerImplementation.dtos;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

import com.codergy.userservice.ScalerImplementation.models.Role;
import com.codergy.userservice.ScalerImplementation.models.User;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles = new HashSet<>();

    public static UserDto from(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}

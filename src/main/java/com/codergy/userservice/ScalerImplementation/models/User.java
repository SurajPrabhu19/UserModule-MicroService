package com.codergy.userservice.ScalerImplementation.models;

import com.codergy.userservice.ScalerImplementation.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@JsonDeserialize(as = User.class)
public class User extends BaseModel {
    private String email;
    private String password;

    @ManyToMany
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    // public static User from(UserDto userDto)
    // {
    //     User user = new User();
    //     user.setEmail(userDto.getEmail());
    //     user.setPassword(userDto.getPassword());
    //     return user;
    // }
}

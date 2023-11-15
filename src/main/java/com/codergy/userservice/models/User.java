package com.codergy.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    private String name;
    private String email;
    private String phoneNo;
    private String password;
}

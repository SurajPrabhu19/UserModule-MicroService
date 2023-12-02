package com.codergy.userservice.models;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// assume one user can have only 5 active sessions at a time.
public class Session extends BaseModel {
    private String token;
    @ManyToOne
    @NonNull
    @JoinColumn(name = "user_id")
    private User user;
}

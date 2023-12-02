package com.codergy.userservice.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codergy.userservice.models.Session;
import com.codergy.userservice.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

    public User findByEmail(String email);

    // public Session findByToken(UUID id);
    
}

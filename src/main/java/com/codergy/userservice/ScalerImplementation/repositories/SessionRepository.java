package com.codergy.userservice.ScalerImplementation.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codergy.userservice.ScalerImplementation.models.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>{
    
    public Optional<Session> findByToken(String token);
}

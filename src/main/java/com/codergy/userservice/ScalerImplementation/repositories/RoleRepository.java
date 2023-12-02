package com.codergy.userservice.ScalerImplementation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codergy.userservice.ScalerImplementation.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}

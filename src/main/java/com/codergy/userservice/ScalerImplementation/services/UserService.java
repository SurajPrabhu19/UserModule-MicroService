package com.codergy.userservice.ScalerImplementation.services;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

import com.codergy.userservice.ScalerImplementation.dtos.UserDto;
import com.codergy.userservice.ScalerImplementation.models.Role;
import com.codergy.userservice.ScalerImplementation.models.User;
import com.codergy.userservice.ScalerImplementation.repositories.RoleRepository;
import com.codergy.userservice.ScalerImplementation.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserById(Long id) throws Exception {
        User user = userRepository.getReferenceById(id);
        if(user == null) throw new Exception("User not found");
        return UserDto.from(user);
    }

    public UserDto setUserRoles(Long id, List<Long> roleIds) throws Exception {
        User user = userRepository.findById(id).get();
        if(user == null) throw new Exception("User Not found");

        List<Role> roles = roleRepository.findAllById(roleIds);

        user.setRoles(Set.copyOf(roles));

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);

    }
    
}

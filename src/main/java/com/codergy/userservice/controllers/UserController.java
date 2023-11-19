package com.codergy.userservice.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codergy.userservice.dtos.UserRequestDto;
import com.codergy.userservice.dtos.UserResponseDto;
import com.codergy.userservice.services.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }
    
    // get mapping:
    // /
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    // post mapping:

    // /users - sign up the user by storing data into the database:
    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> signUpUser(@RequestBody UserRequestDto userDetails)
    {
        return new ResponseEntity<UserResponseDto>(userService.signUpUser(userDetails), HttpStatus.OK);
    }
    
    // /login - login to the application by validating the token in session table
    @PostMapping("/login")
    public ResponseEntity<HttpStatus> loginUser(@RequestBody UserRequestDto userDetails) throws Exception
    {
        return new ResponseEntity<HttpStatus>(userService.loginUser(userDetails));
    }
    
    // /logout - logout - removes or invalidates the session for user in the session tables
    @DeleteMapping("/logout/{id}")
    public ResponseEntity<UserResponseDto> logoutUser(@PathVariable String id) throws Exception
    {
        return new ResponseEntity<UserResponseDto>(userService.logoutUser(UUID.fromString(id)), HttpStatus.OK);
    }

}

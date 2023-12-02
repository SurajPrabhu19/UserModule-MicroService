package com.codergy.userservice.ScalerImplementation.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codergy.userservice.ScalerImplementation.dtos.SetUserRolesDto;
import com.codergy.userservice.ScalerImplementation.dtos.UserDto;
import com.codergy.userservice.ScalerImplementation.models.User;
import com.codergy.userservice.ScalerImplementation.services.UserService;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Object;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken ,@PathVariable Long id) throws Exception
    {
        UserDto userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    
    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable Long id, @RequestBody SetUserRolesDto request) throws Exception
    {
        UserDto userDto = userService.setUserRoles(id, request.getRoleIds());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    
}

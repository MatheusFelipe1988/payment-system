package com.aplication.paymentsystem.controller;


import com.aplication.paymentsystem.domain.DTO.UserR;
import com.aplication.paymentsystem.domain.User;
import com.aplication.paymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody UserR userR){
        User user = userR.toModel();
        User usersave = service.registerUser(user);
        return ResponseEntity.ok().body(usersave);
    }
}

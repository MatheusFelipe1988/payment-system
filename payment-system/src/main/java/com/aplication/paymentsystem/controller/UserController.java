package com.aplication.paymentsystem.controller;


import com.aplication.paymentsystem.domain.DTO.UserR;
import com.aplication.paymentsystem.domain.DTO.UserResponse;
import com.aplication.paymentsystem.domain.User;
import com.aplication.paymentsystem.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserR userR) throws MessagingException,
            UnsupportedEncodingException {
        User user = userR.toModel();
        UserResponse usersave = service.registerUser(user);
        return ResponseEntity.ok().body(usersave);
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code){
        if(service.verify(code)){
            return "verify_sucess";
        }else {
            return "fail_verify";
        }
    }
    @GetMapping("/teste")
    public String teste(){
        return "login sucess";
    }
}

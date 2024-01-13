package com.aplication.paymentsystem.controller;

import com.aplication.paymentsystem.domain.DTO.AuthenticationResponse;
import com.aplication.paymentsystem.domain.DTO.LoginDTO;
import com.aplication.paymentsystem.domain.User;
import com.aplication.paymentsystem.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                loginDTO.email(), loginDTO.senha()
        );

        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}

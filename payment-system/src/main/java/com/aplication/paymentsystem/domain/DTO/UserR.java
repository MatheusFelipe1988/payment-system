package com.aplication.paymentsystem.domain.DTO;

import com.aplication.paymentsystem.domain.User;

public record UserR(String nome, String email, String senha) {
    public User toModel(){
        return new User(nome,email,senha);
    }
}

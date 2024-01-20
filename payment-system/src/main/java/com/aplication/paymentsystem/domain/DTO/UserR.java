package com.aplication.paymentsystem.domain.DTO;

import com.aplication.paymentsystem.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserR(@NotNull(message = "Nome é obrigatório") @NotBlank(message = "Campo obrigatório") String nome,
                    @NotNull(message = "Email é obrigatório") @NotBlank(message = "Campo obrigatório")
                    @Email
                    String email,
                    @NotNull(message = "Senha é obrigatório") @NotBlank(message = "Campo obrigatório")
                    @Size(min = 8, message = "A senha deve no minimo possuir 8 caracteres")
                    String senha,
                    @NotNull(message = "Senha é obrigatório") @NotBlank(message = "Campo obrigatório")
                    @Size(min = 8, message = "A senha deve no minimo possuir 8 caracteres")
                    String role
) {


    public User toModel(){
        return new User(nome,email,senha,role);
    }
}
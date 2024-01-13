package com.aplication.paymentsystem.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@Entity(name = "Users")
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String verificationCode;
    private boolean enabled;

    public User(Long id, String nome, String email, String senha, String verificationCode, boolean enabled) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.verificationCode = verificationCode;
        this.enabled = enabled;
    }

    public User(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled(){
        return this.enabled;
    }
}

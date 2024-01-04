package com.aplication.paymentsystem.service;

import com.aplication.paymentsystem.config.RandomString;
import com.aplication.paymentsystem.domain.User;
import com.aplication.paymentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user){
        if(repository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("This email exist");
        }else{
            String encodedSenha = passwordEncoder.encode(user.getPassword());
            user.setSenha(encodedSenha);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

            User Usersalvo = repository.save(user);

            return Usersalvo;
        }
    }
}

package com.aplication.paymentsystem.service;

import com.aplication.paymentsystem.config.RandomString;
import com.aplication.paymentsystem.domain.DTO.UserResponse;
import com.aplication.paymentsystem.domain.User;
import com.aplication.paymentsystem.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    public UserResponse registerUser(User user) throws MessagingException, UnsupportedEncodingException {
        if(repository.findByEmail(user.getEmail()) != null){
            throw new RuntimeException("This email exist");
        }else{
            String encodedSenha = passwordEncoder.encode(user.getPassword());
            user.setSenha(encodedSenha);

            String randomCode = RandomString.generateRandomString(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

            User Usersalvo = repository.save(user);

            UserResponse userResponse = new UserResponse(Usersalvo.getId(),
                    Usersalvo.getNome(),
                    Usersalvo.getEmail(),
                    Usersalvo.getSenha());

            mailService.sendVerificationMail(user);
            return userResponse;
        }
    }
    public boolean verify(String verificationCode){
        User user = repository.findByVerificationCode(verificationCode);
        if (user == null || user.isEnabled()){
            return false;
        }else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repository.save(user);

            return true;
        }
    }
}

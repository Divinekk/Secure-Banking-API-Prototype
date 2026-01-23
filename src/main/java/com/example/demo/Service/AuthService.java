package com.example.demo.Service;

import com.example.demo.DTO.RegisterRequest;
import com.example.demo.Entity.Users;
import com.example.demo.Exception.DuplicateEmailException;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public Users register(RegisterRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already registered: " + request.getEmail());
        }

        String hash = passwordEncoder.encode(request.getPassword());

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setPasswordHash(hash);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setRole("USER");

        Users savedUser = userRepository.save(user);

        return savedUser;
    }
}

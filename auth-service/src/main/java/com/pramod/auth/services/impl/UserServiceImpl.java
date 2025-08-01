package com.pramod.auth.services.impl;

import com.pramod.auth.dto.UserDto;
import com.pramod.auth.entities.AuthUserEntity;
import com.pramod.auth.repositories.UserRepository;
import com.pramod.auth.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthUserEntity createUser(UserDto userDto) {
        AuthUserEntity user = new AuthUserEntity();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setAccountCreatedAt(new Date().toString());
        user.setAccountStatus("Active");
        return userRepository.save(user);
    }

    @Override
    public List<AuthUserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AuthUserEntity getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }
}

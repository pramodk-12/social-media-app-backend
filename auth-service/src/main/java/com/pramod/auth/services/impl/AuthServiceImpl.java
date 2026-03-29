package com.pramod.auth.services.impl;

import com.pramod.auth.dto.LoginRequestDto;
import com.pramod.auth.dto.RegisterRequestDto;
import com.pramod.auth.entities.AuthUserEntity;
import com.pramod.auth.kafka.KafkaProducer;
import com.pramod.auth.repositories.UserRepository;
import com.pramod.common.security.JwtTokenUtil;
import com.pramod.auth.services.AuthService;
import com.pramod.common.events.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final KafkaProducer kafkaProducer;

    @Override
    public String register(RegisterRequestDto request) {
        if(userRepository.findByUsername((request.getUsername())).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        AuthUserEntity user = AuthUserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .accountCreatedAt(Instant.now().toString())
                .accountStatus("ACTIVE")
                .build();
        userRepository.save(user);

        UserCreatedEvent event = new UserCreatedEvent(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAccountCreatedAt()
        );

        kafkaProducer.sendUserCreatedEvent(event);
        return "User registered successfully";
    }


    @Override
    public String login(LoginRequestDto request) {
        AuthUserEntity user = userRepository.findByUsername((request.getUsername()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if(!passwordEncoder.matches(request.getPassword(),user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtTokenUtil.generateToken(user.getUsername(),user.getId());
    }
}

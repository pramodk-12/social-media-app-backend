package com.pramod.auth.controllers;

import com.pramod.auth.dto.LoginRequestDto;
import com.pramod.auth.dto.RegisterRequestDto;
import com.pramod.auth.entities.AuthUserEntity;
import com.pramod.auth.kafka.KafkaProducer;
import com.pramod.auth.repositories.UserRepository;
import com.pramod.auth.security.JwtTokenUtil;
import com.pramod.events.UserCreatedEvent;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private KafkaProducer kafkaProducer;

    // POST: /auth/login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        Optional<AuthUserEntity> userOpt = userRepository.findByUsername(loginRequest.getUsername());

        if (userOpt.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOpt.get().getPasswordHash())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials!"));
        }

        String token = jwtTokenUtil.generateToken(userOpt.get());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(401).body(Map.of("message", "Username already taken!"));
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(401).body(Map.of("message", "Email already registered!"));

        }

        AuthUserEntity user = AuthUserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .accountCreatedAt(Instant.now().toString())
                .accountStatus("ACTIVE")
                .build();

        userRepository.save(user);
        UserCreatedEvent event = new UserCreatedEvent(user.getId(), user.getUsername(), user.getEmail(), user.getAccountCreatedAt());
        kafkaProducer.sendUserCreatedEvent(event);
        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }


}

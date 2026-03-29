package com.pramod.auth.controllers;

import com.pramod.auth.dto.LoginRequestDto;
import com.pramod.auth.dto.RegisterRequestDto;
import com.pramod.auth.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(Map.of(
                "message", authService.register(request)
        ));
    }
}
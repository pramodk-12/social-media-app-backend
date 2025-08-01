package com.pramod.auth.controllers;

import com.pramod.auth.entities.AuthUserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<AuthUserEntity> getProfile(@AuthenticationPrincipal AuthUserEntity user) {
        return ResponseEntity.ok(user);
    }
}

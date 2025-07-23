package com.pramod.auth.controllers;

import com.pramod.auth.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> getProfile(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(user);
    }
}

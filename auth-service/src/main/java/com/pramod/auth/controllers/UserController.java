package com.pramod.auth.controllers;

import com.pramod.auth.dto.AuthUserProfileDto;
import com.pramod.auth.entities.AuthUserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<AuthUserProfileDto> getProfile(@AuthenticationPrincipal AuthUserEntity user) {
        AuthUserProfileDto dto = new AuthUserProfileDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setAccountCreatedAt(user.getAccountCreatedAt());

        return ResponseEntity.ok(dto);
    }
}

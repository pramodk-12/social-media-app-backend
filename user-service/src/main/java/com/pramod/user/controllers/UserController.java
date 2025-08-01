package com.pramod.user.controllers;

import com.pramod.user.dto.UserDto;
import com.pramod.user.entities.UserEntity;
import com.pramod.user.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping
//    public ResponseEntity<UserEntity> create(@RequestBody UserDto dto) {
//        return ResponseEntity.ok(userService.createUser(dto));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserEntity> getByUsername(@PathVariable String username) {
        return ResponseEntity.of(userService.getUserByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> update(@PathVariable String id, @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}


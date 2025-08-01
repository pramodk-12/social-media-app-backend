package com.pramod.user.services;

import com.pramod.user.dto.UserDto;
import com.pramod.user.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(UserDto dto);
    UserEntity getUserById(String id);
    Optional<UserEntity> getUserByUsername(String username);
    List<UserEntity> getAllUsers();
    UserEntity updateUser(String id, UserDto dto);
    void deleteUser(String id);
}


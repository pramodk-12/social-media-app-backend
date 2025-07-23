package com.pramod.auth.services;

import com.pramod.auth.dto.UserDto;
import com.pramod.auth.entities.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserDto userDto);
    List<UserEntity> getAllUsers();
    UserEntity getUserById(String id);
}

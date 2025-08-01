package com.pramod.auth.services;

import com.pramod.auth.dto.UserDto;
import com.pramod.auth.entities.AuthUserEntity;

import java.util.List;

public interface UserService {
    AuthUserEntity createUser(UserDto userDto);
    List<AuthUserEntity> getAllUsers();
    AuthUserEntity getUserById(String id);
}

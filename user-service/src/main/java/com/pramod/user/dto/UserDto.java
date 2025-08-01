package com.pramod.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private String name;
    private String bio;
    private String accountCreatedAt;
}

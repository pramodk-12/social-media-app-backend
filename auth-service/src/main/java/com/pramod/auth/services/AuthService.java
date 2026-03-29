package com.pramod.auth.services;

import com.pramod.auth.dto.LoginRequestDto;
import com.pramod.auth.dto.RegisterRequestDto;

public interface AuthService {
    String register(RegisterRequestDto request);
    String login(LoginRequestDto request);
}

package com.example.office.config.security.auth.services;

import com.example.office.config.security.auth.requests.LoginRequest;
import com.example.office.config.security.auth.requests.RegisterRequest;
import com.example.office.config.security.auth.responses.JWTTokenResponse;
import com.example.office.domain.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    JWTTokenResponse register(User user, String password, boolean enabled);

    JWTTokenResponse register(RegisterRequest request, boolean enabled);

    Authentication getAuthentication();

    JWTTokenResponse authenticate(LoginRequest request);
}
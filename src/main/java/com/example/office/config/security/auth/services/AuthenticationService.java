package com.example.office.config.security.auth.services;

import com.example.office.config.security.auth.JwtService;
import com.example.office.config.security.auth.jwt.TokenEntity;
import com.example.office.config.security.auth.jwt.TokenRepository;
import com.example.office.config.security.auth.jwt.TokenType;
import com.example.office.config.security.auth.requests.LoginRequest;
import com.example.office.config.security.auth.requests.RegisterRequest;
import com.example.office.config.security.auth.responses.JWTTokenResponse;
import com.example.office.domain.User;
import com.example.office.dtos.mappers.UserMapper;
import com.example.office.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    @Transactional
    public JWTTokenResponse register(User user, String password, boolean enabled) {
        log.debug("Register  user: {}", userMapper.toDto(user));

        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(enabled);

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return JWTTokenResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Transactional
    public JWTTokenResponse register(RegisterRequest request, boolean enabled) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of())
                .build();

        return this.register(user, request.getPassword(), enabled);
    }

    public JWTTokenResponse authenticate(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        authenticationManager.authenticate(authenticationToken);

        var user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return JWTTokenResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
package com.example.office.config.security.auth;

import com.example.office.domain.User;
import com.example.office.dtos.mappers.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final Logger log = LoggerFactory.getLogger(JwtService.class);

    private static final String AUTHORITIES_KEY = "authorities";
    public static final String ROLES = "roles";
    public static final String PERMISSIONS = "permissions";
    private static final String USERINFO = "userinfo";

    private Key key;

    @Value("${jwt.base64-secret-key}")
    private String jwtKey;

    @Value("${jwt.token_validation_in_milliseconds}")
    private long tokenValidityInMilliseconds;

    private final UserMapper userMapper;

    public JwtService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User userDetails) {
        log.debug("Generate token for user: {}", userDetails.getEmail());

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put(AUTHORITIES_KEY, userDetails.getAuthorities());
        extraClaims.put(USERINFO, userMapper.toDto(userDetails));
        return generateToken(extraClaims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + this.tokenValidityInMilliseconds))
            .signWith(getSignInKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        return key;
    }
}

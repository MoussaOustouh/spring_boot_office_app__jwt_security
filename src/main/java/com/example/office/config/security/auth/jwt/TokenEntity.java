package com.example.office.config.security.auth.jwt;

import com.example.office.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
public class TokenEntity {
    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true, length = 500)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @Column()
    public boolean revoked;

    @Column()
    public boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}

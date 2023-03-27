package com.example.office.config.security.auth.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class LoginRequest {

    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 255)
    @ToString.Exclude
    private String password;

    private boolean rememberMe;
}

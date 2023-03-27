package com.example.office.services;

import com.example.office.dtos.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUserByEmail(String email);

    Optional<UserDTO> registerUser(UserDTO user, String password);
}

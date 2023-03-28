package com.example.office.services;

import com.example.office.domain.User;
import com.example.office.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUserByEmail(String email);

    Optional<UserDTO> registerUser(UserDTO user, String password);

    Page<UserDTO> findAll(final Specification<User> specification, Pageable pageable);
}

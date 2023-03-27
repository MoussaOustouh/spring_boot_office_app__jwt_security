package com.example.office.services.impl;

import com.example.office.domain.User;
import com.example.office.dtos.UserDTO;
import com.example.office.dtos.mappers.UserMapper;
import com.example.office.exceptions.EmailAlreadyUsedException;
import com.example.office.repositories.UserRepository;
import com.example.office.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        log.debug("Request to get User by email : {}", email);
        return  userRepository.findByEmailIgnoreCase(email).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDTO> registerUser(UserDTO userDTO, String password) {
        userRepository.findByEmailIgnoreCase(userDTO.getEmail())
            .ifPresent(user -> {
                throw new EmailAlreadyUsedException();
            });

        User newUser = userMapper.toEntity(userDTO);
        newUser.setId(null);
        newUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(newUser);
        log.debug("Created information for Use: {}", newUser);

        return Optional.of(userMapper.toDto(newUser));
    }
}

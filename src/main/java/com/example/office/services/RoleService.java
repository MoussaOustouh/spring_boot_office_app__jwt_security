package com.example.office.services;

import com.example.office.domain.Permission;
import com.example.office.domain.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Role createRoleIfNotFound(String name, Set<Permission> permissions);
}

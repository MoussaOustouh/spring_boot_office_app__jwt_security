package com.example.office.services.impl;

import com.example.office.domain.Permission;
import com.example.office.domain.Role;
import com.example.office.repositories.RoleRepository;
import com.example.office.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Role createRoleIfNotFound(String name, Set<Permission> permissions) {
        Optional<Role> optionalRole = roleRepository.findByName(name);

        if (optionalRole.isEmpty()) {
            Role role = new Role(name, permissions);
            roleRepository.save(role);
            return role;
        }
        return optionalRole.get();
    }
}

package com.example.office.services.impl;

import com.example.office.domain.Permission;
import com.example.office.domain.Role;
import com.example.office.repositories.RoleRepository;
import com.example.office.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Role createRoleIfNotFound(String name, Set<Permission> permissions) {
        log.debug("Create {} role if not found.", name);
        Optional<Role> optionalRole = roleRepository.findByName(name);

        if (optionalRole.isEmpty()) {
            Role role = new Role(name, permissions);
            roleRepository.save(role);
            log.debug("The {} role created.", name);
            return role;
        }
        return optionalRole.get();
    }
}

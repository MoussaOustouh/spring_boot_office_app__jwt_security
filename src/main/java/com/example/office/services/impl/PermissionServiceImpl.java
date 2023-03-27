package com.example.office.services.impl;

import com.example.office.domain.Permission;
import com.example.office.repositories.PermissionRepository;
import com.example.office.services.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final Logger log = LoggerFactory.getLogger(PermissionServiceImpl.class);
    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public Permission createPermissionIfNotFound(String name) {
        log.debug("Create {} permission if not found.", name);
        Optional<Permission> optionalPermission = permissionRepository.findByName(name);

        if (optionalPermission.isEmpty()) {
            Permission permission = new Permission(name);
            permissionRepository.save(permission);
            log.debug("The {} permission created.", name);
            return permission;
        }
        return optionalPermission.get();
    }
}

package com.example.office.config.init;

import com.example.office.constants.AppConstants;
import com.example.office.domain.Permission;
import com.example.office.domain.Role;
import com.example.office.repositories.PermissionRepository;
import com.example.office.repositories.RoleRepository;
import com.example.office.repositories.UserRepository;
import com.example.office.services.PermissionService;
import com.example.office.services.RoleService;
import com.example.office.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    private final UserService userService;
    private final PermissionService permissionService;
    private final RoleService roleService;

    public SetupDataLoader(UserService userService, PermissionService permissionService, RoleService roleService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        Permission createPermission = permissionService.createPermissionIfNotFound(AppConstants.Permissions.CREATE);
        Permission viewPermission = permissionService.createPermissionIfNotFound(AppConstants.Permissions.VIEW);
        Permission deletePermission = permissionService.createPermissionIfNotFound(AppConstants.Permissions.DELETE);

        Role superAdminRole = roleService.createRoleIfNotFound(AppConstants.Roles.SUPER_ADMIN, Set.of(viewPermission, createPermission, deletePermission));
        Role adminRole = roleService.createRoleIfNotFound(AppConstants.Roles.ADMIN, Set.of(viewPermission, createPermission));
        Role userRole = roleService.createRoleIfNotFound(AppConstants.Roles.USER, Set.of(viewPermission));



        alreadySetup = true;
    }


}

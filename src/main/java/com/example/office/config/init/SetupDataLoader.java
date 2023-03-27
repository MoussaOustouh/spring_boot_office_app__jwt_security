package com.example.office.config.init;

import com.example.office.config.security.auth.services.AuthenticationService;
import com.example.office.constants.AppConstants;
import com.example.office.domain.Permission;
import com.example.office.domain.Role;
import com.example.office.domain.User;
import com.example.office.dtos.mappers.RoleMapper;
import com.example.office.services.PermissionService;
import com.example.office.services.RoleService;
import com.example.office.services.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    private final UserService userService;
    private final PermissionService permissionService;
    private final RoleService roleService;

    private final RoleMapper roleMapper;

    private final AuthenticationService authenticationService;

    public SetupDataLoader(UserService userService, PermissionService permissionService, RoleService roleService, RoleMapper roleMapper, AuthenticationService authenticationService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.roleMapper = roleMapper;
        this.authenticationService = authenticationService;
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


        User u1 = new User();
        u1.setEmail("user1@gmail.com");
        u1.setPassword("12345");
        u1.setFirstName("User1");
        u1.setLastName("User1");
        u1.getRoles().add(superAdminRole);

        User u2 = new User();
        u2.setEmail("user2@gmail.com");
        u2.setPassword("12345");
        u2.setFirstName("User2");
        u2.setLastName("User2");
        u2.getRoles().add(adminRole);

        User u3 = new User();
        u3.setEmail("user3@gmail.com");
        u3.setPassword("12345");
        u3.setFirstName("User3");
        u3.setLastName("User3");
        u3.getRoles().add(userRole);

        authenticationService.register(u1, "12345", true);
        authenticationService.register(u2, "12345", true);
        authenticationService.register(u3, "12345", true);

        alreadySetup = true;
    }
}

package com.example.office.web.controllers;

import com.example.office.constants.AppConstants;
import com.example.office.domain.Form;
import com.example.office.domain.Form_;
import com.example.office.domain.Role;
import com.example.office.domain.Role_;
import com.example.office.domain.User;
import com.example.office.domain.User_;
import com.example.office.dtos.FormDTO;
import com.example.office.dtos.UserDTO;
import com.example.office.services.FormService;
import com.example.office.services.UserService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private FormService formService;
    @Autowired
    private UserService userService;

    @GetMapping("/app/api/v1/users/{userId}/forms")
    public ResponseEntity<Page<FormDTO>> getUserForms(@PathVariable Long userId, Pageable pageable) {

        Specification<Form> specification = Specification.where(
                (root, query, criteriaBuilder) -> {
                    Join<Form, User> usersForm = root.join(Form_.USER, JoinType.INNER);
                    return criteriaBuilder.equal(usersForm.get(User_.id), userId);
                }
        );

        Page<FormDTO> page = this.formService.findAll(specification, pageable);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/app/api/v1/users/USER")
    public ResponseEntity<Page<UserDTO>> getUsersWithRoleUSER(Pageable pageable) {

        Specification<User> specification = Specification.where(
                (root, query, criteriaBuilder) -> {
                    Join<User, Role> usersForm = root.join(User_.ROLES, JoinType.INNER);
                    return criteriaBuilder.equal(usersForm.get(Role_.name), AppConstants.Roles.USER);
                }
        );

        Page<UserDTO> page = this.userService.findAll(specification, pageable);

        return ResponseEntity.ok(page);
    }
}

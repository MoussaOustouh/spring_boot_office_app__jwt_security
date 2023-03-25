package com.example.office.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = User.TABLE_NAME)
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "app_user";
    private static final String SEQUENCE_GENERATOR = TABLE_NAME+"_sequence";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = User.SEQUENCE_GENERATOR)
    @SequenceGenerator(name = User.SEQUENCE_GENERATOR, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Max(255)
    @Column(nullable = false)
    private String firstName;

    @Max(255)
    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false)
    private String email;

    @Max(255)
    @Column(name = "password_hash")
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = User_.ID),
            inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = Role_.NAME)
    )
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled && Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, enabled, roles);
    }
}

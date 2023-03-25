package com.example.office.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    private Set<PermissionDTO> permissions = new HashSet<>();
}

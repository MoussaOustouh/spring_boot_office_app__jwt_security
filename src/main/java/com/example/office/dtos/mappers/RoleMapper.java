package com.example.office.dtos.mappers;

import com.example.office.domain.Role;
import com.example.office.dtos.RoleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Role} and its DTO {@link RoleDTO}
 */

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<Role, RoleDTO> {
}

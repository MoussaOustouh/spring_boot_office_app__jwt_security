package com.example.office.dtos.mappers;

import com.example.office.domain.Permission;
import com.example.office.dtos.PermissionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Permission} and its DTO {@link PermissionDTO}
 */

@Mapper(componentModel = "spring")
public interface PermissionMapper extends EntityMapper<Permission, PermissionDTO>{
}

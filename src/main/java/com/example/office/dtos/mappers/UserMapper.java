package com.example.office.dtos.mappers;

import com.example.office.domain.User;
import com.example.office.dtos.UserDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}
 */

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDTO>{
}

package com.example.office.dtos.mappers;

import com.example.office.domain.Form;
import com.example.office.dtos.FormDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Form} and its DTO {@link FormDTO}
 */

@Mapper(componentModel = "spring")
public interface FormMapper extends EntityMapper<Form, FormDTO>{
}

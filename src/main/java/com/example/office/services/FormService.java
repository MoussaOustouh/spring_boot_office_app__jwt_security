package com.example.office.services;

import com.example.office.domain.Form;
import com.example.office.dtos.FormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface FormService {
    FormDTO create(FormDTO formDTO, Authentication authentication);
    Optional<FormDTO> findOne(Long id);
    Page<FormDTO> findAll(Pageable pageable);
    Page<FormDTO> findAll(final Specification<Form> specification, Pageable pageable);
}

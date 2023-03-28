package com.example.office.services.impl;

import com.example.office.config.security.auth.services.AuthenticationService;
import com.example.office.domain.Form;
import com.example.office.domain.User;
import com.example.office.dtos.FormDTO;
import com.example.office.dtos.mappers.FormMapper;
import com.example.office.repositories.FormRepository;
import com.example.office.services.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FormServiceImpl implements FormService {
    @Autowired
    private FormRepository formRepository;

    @Autowired
    private FormMapper formMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    @Transactional
    public FormDTO create(FormDTO formDTO, Authentication authentication) {
        Form form = formMapper.toEntity(formDTO);
        User userUser = (User) authenticationService.getAuthentication().getPrincipal();
        User u = new User();
        u.setId(userUser.getId());
        form.setUser(u);

        return formMapper.toDto(formRepository.save(form));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormDTO> findOne(Long id) {
        return formRepository.findById(id)
            .map(e -> formMapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormDTO> findAll(Pageable pageable) {
        return formRepository.findAll(pageable)
                .map(e -> formMapper.toDto(e));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormDTO> findAll(final Specification<Form> specification, Pageable pageable) {
        return formRepository.findAll(specification, pageable)
                .map(e -> formMapper.toDto(e));
    }
}

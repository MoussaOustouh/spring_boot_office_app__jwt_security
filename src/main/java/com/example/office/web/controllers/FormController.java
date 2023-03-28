package com.example.office.web.controllers;

import com.example.office.domain.enums.FormStatus;
import com.example.office.dtos.FormDTO;
import com.example.office.exceptions.NotFoundException;
import com.example.office.services.FormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
public class FormController {

    @Autowired
    private FormService formService;

    @PostMapping("/app-no-vpn/api/v1/forms")
    public ResponseEntity<FormDTO> saveFormOnSite(@Valid @RequestBody FormDTO form, Authentication authentication) {
        form.setStatus(FormStatus.ON_SITE);
        return ResponseEntity.ok(formService.create(form, authentication));
    }

    @PostMapping("/app/api/v1/forms")
    public ResponseEntity<FormDTO> saveFormRemotely(@Valid @RequestBody FormDTO form, Authentication authentication) {
        form.setStatus(FormStatus.REMOTELY);
        return ResponseEntity.ok(formService.create(form, authentication));
    }

    @GetMapping("/app/api/v1/forms/{id}")
    public ResponseEntity<FormDTO> get(@PathVariable Long id) {
        Optional<FormDTO> optionalForm = formService.findOne(id);

        return optionalForm.map(ResponseEntity::ok)
                .orElseThrow(
                        () -> new NotFoundException(String.format("Form not found (id: %d)", id))
                );
    }

    @GetMapping("/app/api/v1/forms")
    public ResponseEntity<Page<FormDTO>> getForms(Pageable pageable) {
        Page<FormDTO> page = this.formService.findAll(pageable);

        return ResponseEntity.ok(page);
    }
}

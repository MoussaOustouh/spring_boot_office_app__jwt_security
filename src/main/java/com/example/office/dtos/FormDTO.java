package com.example.office.dtos;

import com.example.office.domain.enums.FormStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FormDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Instant datetime;

    private FormStatus status;

    private UserDTO user;
}

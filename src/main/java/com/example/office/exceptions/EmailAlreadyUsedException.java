package com.example.office.exceptions;

import java.io.Serial;

public class EmailAlreadyUsedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }
}

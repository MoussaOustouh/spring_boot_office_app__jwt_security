package com.example.office.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class NotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public NotFoundException(String message) {
        this.httpStatus = HttpStatus.NOT_FOUND;
        this.message = message;
    }
}

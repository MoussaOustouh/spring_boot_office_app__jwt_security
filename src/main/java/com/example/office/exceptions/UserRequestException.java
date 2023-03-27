package com.example.office.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

public class UserRequestException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus status;

    /**
     * Error messages
     */
    public static final String MESSAGE_USER_WAS_NOT_FOUND_IN_THE_SYSTEM_DATABASE = "L'utilisateur n'a pas été trouvé dans la base de données système, contactez l'administrateur système";

    public UserRequestException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public UserRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

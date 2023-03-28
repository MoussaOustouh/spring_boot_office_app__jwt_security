package com.example.office.exceptions.handlers;

import com.example.office.exceptions.NotFoundException;
import com.example.office.exceptions.errors.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Object> handlerBadCredentialsException(BadCredentialsException ex, NativeWebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(Instant.now(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(Instant.now(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleOthersExceptions(Exception ex, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(Instant.now(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}

package com.example.office.exceptions.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class ErrorMessage {
    private Instant timestamp;
    private String message;
}
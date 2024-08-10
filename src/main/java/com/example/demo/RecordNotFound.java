package com.example.demo;

import org.springframework.http.HttpStatus;

public class RecordNotFound extends RuntimeException {
    private final HttpStatus status;
    private final String message;


    public RecordNotFound(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

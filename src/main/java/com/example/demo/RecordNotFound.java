package com.example.demo;

import org.springframework.http.HttpStatus;

public class RecordNotFound extends RuntimeException {
    private final String title, message;
    public RecordNotFound(HttpStatus errorCode, String title, String message) {
        this.title = title;
        this.message = message;
    }
}

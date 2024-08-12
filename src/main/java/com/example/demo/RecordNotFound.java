package com.example.demo;

import org.springframework.http.HttpStatus;

public class RecordNotFound extends RuntimeException {
    public RecordNotFound(String message) {
       super(message);
    }
}

package com.example.demo.Exception;

import com.example.demo.Enum.ResponseType;
import com.example.demo.Base.ResponseHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "VALIDATION_ERROR",
                "Validation failed for one or more fields",
                errors,
                ResponseType.ERROR.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.BAD_REQUEST);
    }

    // Handle custom ServerException
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<?> handleServerException(ServerException ex) {
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "SERVER_ERROR",
                ex.getMessage(),
                null,
                ResponseType.ERROR.toString()
        );

        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "GENERIC_ERROR",
                ex.getMessage(),
                null,
                ResponseType.ERROR.toString()
        );

        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
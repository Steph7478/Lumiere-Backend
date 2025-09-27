package com.lumiere.presentation.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lumiere.application.exceptions.EmailAlreadyExistsException;
import com.lumiere.presentation.exceptions.TokenGenerationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailExists(EmailAlreadyExistsException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "EMAIL_EXISTS");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<Map<String, String>> handleTokenException(TokenGenerationException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("error", "TOKEN_ERROR");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}

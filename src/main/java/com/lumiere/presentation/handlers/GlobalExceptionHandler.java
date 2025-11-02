package com.lumiere.presentation.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lumiere.application.exceptions.EmailAlreadyExistsException;
import com.lumiere.application.exceptions.InvalidCredentialsException;
import com.lumiere.application.exceptions.TokenGenerationException;
import com.lumiere.application.exceptions.UserNotFoundException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<Map<String, String>> handleEmailExists(EmailAlreadyExistsException ex) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(Map.of(
                                                "error", "EMAIL_EXISTS",
                                                "message", ex.getMessage()));
        }

        @ExceptionHandler({ UserNotFoundException.class, InvalidCredentialsException.class })
        public ResponseEntity<Map<String, String>> handleAuthErrors(RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of(
                                                "error", ex.getClass().getSimpleName().toUpperCase(),
                                                "message", ex.getMessage()));
        }

        @ExceptionHandler(TokenGenerationException.class)
        public ResponseEntity<Map<String, String>> handleTokenError(TokenGenerationException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of(
                                                "error", "TOKEN_ERROR",
                                                "message", ex.getMessage()));
        }
}

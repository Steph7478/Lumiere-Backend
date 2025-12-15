package com.lumiere.presentation.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lumiere.application.exceptions.auth.EmailAlreadyExistsException;
import com.lumiere.application.exceptions.auth.InvalidCredentialsException;
import com.lumiere.application.exceptions.auth.TokenGenerationException;
import com.lumiere.application.exceptions.auth.UserNotFoundException;
import com.lumiere.application.exceptions.cart.CartNotFoundException;
import com.lumiere.application.exceptions.payment.PaymentGatewayException;
import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.stripe.exception.SignatureVerificationException;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

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

        @ExceptionHandler(SignatureVerificationException.class)
        public ResponseEntity<Map<String, String>> handleSignatureVerificationError(SignatureVerificationException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "error", "WEBHOOK_SIGNATURE_INVALID",
                                                "message", "Failed to verify signature: " + ex.getMessage()));
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

        @ExceptionHandler(ProductNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleProductErrors(ProductNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "error", "INVALID_PRODUCT",
                                                "message", ex.getMessage()));
        }

        @ExceptionHandler(CartNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleCartErrors(CartNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "error", "INVALID_CART",
                                                "message", ex.getMessage()));
        }

        @ExceptionHandler(PaymentGatewayException.class)
        public ResponseEntity<Map<String, String>> handlePaymentGateway(PaymentGatewayException ex) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                                .body(Map.of(
                                                "error", "PAYMENT_SERVICE_UNAVAILABLE",
                                                "message", "Payment service temporarily unavailable"));
        }

        @ExceptionHandler(CallNotPermittedException.class)
        public ResponseEntity<Map<String, String>> handleCircuitBreakerOpen(CallNotPermittedException ex) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body(Map.of(
                                                "error", "CIRCUIT_BREAKER_OPEN",
                                                "message", "Payment service temporarily unavailable"));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "error", "INVALID_ARGUMENT",
                                                "message", ex.getMessage()));
        }
}

package com.lumiere.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Payment {

    private final UUID id;
    private final BigDecimal amount;
    private final String currency;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime paymentDate;

    private Payment(UUID id, BigDecimal amount, String currency, PaymentMethod paymentMethod,
            LocalDateTime paymentDate) {
        this.id = id != null ? id : UUID.randomUUID();
        this.amount = Objects.requireNonNull(amount, "amount cannot be null");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        this.currency = Objects.requireNonNull(currency, "currency cannot be null");
        this.paymentMethod = Objects.requireNonNull(paymentMethod, "paymentMethod cannot be null");
        this.paymentDate = Objects.requireNonNull(paymentDate, "paymentDate cannot be null");
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    // Enum
    public enum PaymentMethod {
        PIX, CREDIT_CARD, DEBIT_CARD, BOLETO
    }

    // factory
    public static Payment createPayment(BigDecimal amount, String currency, PaymentMethod paymentMethod) {
        return new Payment(null, amount, currency, paymentMethod, LocalDateTime.now());
    }
}

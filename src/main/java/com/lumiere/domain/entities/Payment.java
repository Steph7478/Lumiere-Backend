package com.lumiere.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.vo.Money;

public class Payment {

    private final UUID id;
    private final Money amount;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime paymentDate;

    private Payment(UUID id, Money amount, PaymentMethod paymentMethod, LocalDateTime paymentDate) {
        this.id = id != null ? id : UUID.randomUUID();
        this.amount = Objects.requireNonNull(amount, "amount cannot be null");
        this.paymentMethod = Objects.requireNonNull(paymentMethod, "paymentMethod cannot be null");
        this.paymentDate = Objects.requireNonNull(paymentDate, "paymentDate cannot be null");
    }

    // getters
    public UUID getId() {
        return id;
    }

    public Money getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    // enums

    public enum PaymentMethod {
        PIX, CREDIT_CARD, DEBIT_CARD, BOLETO
    }

    // factory
    public static Payment createPayment(Money amount, PaymentMethod method) {
        return new Payment(null, amount, method, LocalDateTime.now());
    }
}

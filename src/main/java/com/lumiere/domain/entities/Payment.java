package com.lumiere.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import com.lumiere.domain.enums.PaymentMethodEnum;

public class Payment {
    private final String id;
    private final PaymentMethodEnum paymentMethod;
    private final LocalDateTime paymentDate;

    public Payment(String id, PaymentMethodEnum paymentMethod, LocalDateTime paymentDate) {
        this.id = id;
        this.paymentMethod = Objects.requireNonNull(paymentMethod, "paymentMethod cannot be null");
        this.paymentDate = Objects.requireNonNull(paymentDate, "paymentDate cannot be null");

    }

    // getters
    public String getId() {
        return id;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
}
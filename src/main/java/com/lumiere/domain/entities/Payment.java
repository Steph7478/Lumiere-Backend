package com.lumiere.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;
import com.lumiere.domain.enums.PaymentMethodEnum;

public class Payment extends BaseEntity {
    private final PaymentMethodEnum paymentMethod;
    private final LocalDateTime paymentDate;

    public Payment(UUID id, PaymentMethodEnum paymentMethod, LocalDateTime paymentDate) {
        super(id);
        this.paymentMethod = Objects.requireNonNull(paymentMethod, "paymentMethod cannot be null");
        this.paymentDate = Objects.requireNonNull(paymentDate, "paymentDate cannot be null");

    }

    // getters
    public UUID getId() {
        return super.getId();
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
}
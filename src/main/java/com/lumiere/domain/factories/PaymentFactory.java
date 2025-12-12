package com.lumiere.domain.factories;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lumiere.domain.entities.Payment;
import com.lumiere.domain.enums.PaymentMethodEnum;

public class PaymentFactory {
    public static Payment create(UUID id, PaymentMethodEnum paymentMethod, LocalDateTime paymentDate) {
        return new Payment(id, paymentMethod, paymentDate);
    }
}
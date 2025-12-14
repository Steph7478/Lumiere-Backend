package com.lumiere.domain.factories;

import java.time.LocalDateTime;

import com.lumiere.domain.entities.Payment;
import com.lumiere.domain.enums.PaymentMethodEnum;

public class PaymentFactory {
    public static Payment create(String id, PaymentMethodEnum paymentMethod, LocalDateTime paymentDate) {
        return new Payment(id, paymentMethod, paymentDate);
    }
}
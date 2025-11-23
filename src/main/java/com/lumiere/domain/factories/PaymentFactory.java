package com.lumiere.domain.factories;

import java.time.LocalDateTime;
import java.util.UUID;

import com.lumiere.domain.entities.Payment;
import com.lumiere.domain.entities.Payment.PaymentMethod;
import com.lumiere.domain.vo.Money;

public class PaymentFactory {

    public static Payment from(UUID id, Money amount, PaymentMethod paymentMethod, LocalDateTime paymentDate) {
        return new Payment(id, amount, paymentMethod, paymentDate);
    }
}
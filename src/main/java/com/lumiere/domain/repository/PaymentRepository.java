package com.lumiere.domain.repository;

import com.lumiere.domain.entity.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}

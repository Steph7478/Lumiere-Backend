package com.lumiere.domain.repositories;

import com.lumiere.domain.entities.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}

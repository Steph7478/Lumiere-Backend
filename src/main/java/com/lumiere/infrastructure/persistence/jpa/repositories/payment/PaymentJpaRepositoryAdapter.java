package com.lumiere.infrastructure.persistence.jpa.repositories.payment;

import com.lumiere.domain.entities.Payment;
import com.lumiere.infrastructure.mappers.PaymentMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.PaymentJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

public class PaymentJpaRepositoryAdapter extends BaseRepositoryAdapter<Payment, PaymentJpaEntity> {

    protected PaymentJpaRepositoryAdapter(PaymentJpaRepository jpaRepository,
            PaymentMapper mapper, EntityManager entityManager) {
        super(jpaRepository, mapper, entityManager);
    }

}

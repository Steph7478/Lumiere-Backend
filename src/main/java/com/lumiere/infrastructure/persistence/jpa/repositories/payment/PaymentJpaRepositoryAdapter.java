package com.lumiere.infrastructure.persistence.jpa.repositories.payment;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.Payment;
import com.lumiere.domain.repositories.PaymentRepository;
import com.lumiere.infrastructure.mappers.PaymentMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.PaymentJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

@Repository
public class PaymentJpaRepositoryAdapter extends BaseRepositoryAdapter<Payment, PaymentJpaEntity>
        implements PaymentRepository {

    protected PaymentJpaRepositoryAdapter(PaymentJpaRepository jpaRepository,
            PaymentMapper mapper, EntityManager entityManager) {
        super(jpaRepository, mapper, entityManager);
    }

}

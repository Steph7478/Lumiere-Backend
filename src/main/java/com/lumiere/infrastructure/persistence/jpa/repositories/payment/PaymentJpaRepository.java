package com.lumiere.infrastructure.persistence.jpa.repositories.payment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumiere.infrastructure.persistence.jpa.entities.PaymentJpaEntity;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, UUID> {

}

package com.lumiere.infrastructure.persistence.jpa.repositories.payment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.persistence.jpa.entities.PaymentJpaEntity;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, UUID> {

}

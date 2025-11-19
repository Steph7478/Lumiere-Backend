package com.lumiere.infrastructure.persistence.jpa.repositories.order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumiere.infrastructure.persistence.jpa.entities.OrderJpaEntity;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {

}

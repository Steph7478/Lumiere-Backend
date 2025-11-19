package com.lumiere.infrastructure.persistence.jpa.repositories.cart;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;

public interface CartJpaRepository extends JpaRepository<CartJpaEntity, UUID> {

}

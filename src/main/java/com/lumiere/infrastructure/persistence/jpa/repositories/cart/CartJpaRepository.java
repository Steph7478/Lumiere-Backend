package com.lumiere.infrastructure.persistence.jpa.repositories.cart;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;

@Repository
public interface CartJpaRepository extends JpaRepository<CartJpaEntity, UUID> {

}

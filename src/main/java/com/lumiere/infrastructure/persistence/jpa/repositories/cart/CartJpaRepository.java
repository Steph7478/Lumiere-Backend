package com.lumiere.infrastructure.persistence.jpa.repositories.cart;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.persistence.jpa.entities.CartJpaEntity;

@Repository
public interface CartJpaRepository extends JpaRepository<CartJpaEntity, UUID> {

    @EntityGraph(attributePaths = { "userId" })
    Optional<CartJpaEntity> findByUserId(UUID id);
}

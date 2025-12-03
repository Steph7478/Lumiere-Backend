package com.lumiere.infrastructure.persistence.jpa.repositories.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.infrastructure.persistence.jpa.entities.OrderJpaEntity;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {

    @EntityGraph(attributePaths = { "user", "items", "items.product", "coupon" })
    List<OrderJpaEntity> findByUserId(UUID id);

    boolean existsByUserIdAndStatus(UUID userId, Status status);

    @EntityGraph(attributePaths = { "user", "items", "items.product", "coupon" })
    Optional<OrderJpaEntity> findByUserIdAndStatus(UUID userId, Status status);
}

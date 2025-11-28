package com.lumiere.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.Order;
import com.lumiere.domain.enums.StatusEnum.Status;
import com.lumiere.domain.repositories.base.BaseRepository;

public interface OrderRepository extends BaseRepository<Order> {
    List<Order> findOrderByUserId(UUID id);

    boolean existsByUserIdAndStatus(UUID userId, Status status);

    Optional<Order> findByUserIdAndStatus(UUID userId, Status status);
}

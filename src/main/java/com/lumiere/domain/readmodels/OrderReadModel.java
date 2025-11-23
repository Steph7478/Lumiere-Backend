package com.lumiere.domain.readmodels;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.lumiere.domain.enums.StatusEnum.Status;

public record OrderReadModel(
                UUID id,
                List<OrderItemReadModel> items,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                String coupon, Status status) {
}
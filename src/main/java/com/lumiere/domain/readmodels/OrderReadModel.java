package com.lumiere.domain.readmodels;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.lumiere.domain.enums.StatusEnum.Status;

public record OrderReadModel(
        UUID id,
        Status status,
        String coupon,
        BigDecimal total,
        List<OrderItemReadModel> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
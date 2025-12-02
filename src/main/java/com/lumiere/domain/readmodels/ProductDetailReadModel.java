package com.lumiere.domain.readmodels;

import com.lumiere.domain.enums.CategoriesEnum;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.entities.Rating;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProductDetailReadModel(
        UUID id,
        String name,
        String description,
        Money price,
        List<Rating> ratings,
        int stock,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        CategoriesEnum.Category category,
        CategoriesEnum.SubCategory subCategory) {
}
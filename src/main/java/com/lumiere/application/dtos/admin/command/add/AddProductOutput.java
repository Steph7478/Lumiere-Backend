package com.lumiere.application.dtos.admin.command.add;

import com.lumiere.domain.enums.CategoriesEnum;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.entities.Rating;

import java.time.LocalDateTime;
import java.util.List;

public record AddProductOutput(
                String id,
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
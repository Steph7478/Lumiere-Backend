package com.lumiere.application.dtos.admin.command.add;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.enums.CategoriesEnum;

public record AddProductOutput(Product product, CategoriesEnum.Category category,
        CategoriesEnum.SubCategory subCategory) {
}

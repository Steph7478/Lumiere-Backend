package com.lumiere.application.dtos.admin.command.add;

import java.math.BigDecimal;

import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;

public record AddProductRequestData(String name, String description, BigDecimal priceAmount, CurrencyType currency,
        int stockQuantity, Category category, SubCategory subcategory) {
}

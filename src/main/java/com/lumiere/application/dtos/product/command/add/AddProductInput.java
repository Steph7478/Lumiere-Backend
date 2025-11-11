package com.lumiere.application.dtos.product.command.add;

import java.math.BigDecimal;

import com.lumiere.domain.enums.CategoriesEnum.*;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;

public record AddProductInput(String name, String description, BigDecimal priceAmount, CurrencyType currency,
                Integer stockQuantity, Category category, SubCategory subcategory) {
}

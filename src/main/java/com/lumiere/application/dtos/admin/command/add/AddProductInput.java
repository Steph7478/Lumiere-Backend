package com.lumiere.application.dtos.admin.command.add;

import java.math.BigDecimal;

import com.lumiere.domain.enums.CategoriesEnum.*;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.vo.Stock;

public record AddProductInput(String name, String description, BigDecimal priceAmount,
        CurrencyType currency,
        Stock stockQuantity, Category category, SubCategory subcategory) {
}

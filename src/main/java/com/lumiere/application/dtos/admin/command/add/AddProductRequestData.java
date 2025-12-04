package com.lumiere.application.dtos.admin.command.add;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;

public record AddProductRequestData(String name, String description, BigDecimal priceAmount, String currency,
        int stockQuantity, Category category, SubCategory subcategory, MultipartFile imageFile) {
}

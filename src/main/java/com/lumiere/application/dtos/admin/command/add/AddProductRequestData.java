package com.lumiere.application.dtos.admin.command.add;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;

import jakarta.validation.constraints.*;

public record AddProductRequestData(@NotBlank String name, @NotBlank String description,
                @NotNull BigDecimal priceAmount, @NotBlank String currency,
                @NotNull int stockQuantity, @NotNull Category category, @NotNull SubCategory subcategory,
                @NotNull MultipartFile imageFile) {
}

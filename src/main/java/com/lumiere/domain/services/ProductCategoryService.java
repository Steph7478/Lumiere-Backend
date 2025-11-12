package com.lumiere.domain.services;

import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;
import com.lumiere.domain.vo.Money;

public class ProductCategoryService {
    public static ProductCategory createProductCategory(UUID productId, Category category, SubCategory subcategory) {
        return ProductCategory.createProductCategory(productId, category, subcategory);
    }

    public static void update(Product product, Optional<String> name, Optional<String> description) {
        product.updateProduct(name, description);
    }

    public static Product increaseStock(Product product, int quantity) {
        return product.increaseStock(quantity);
    }

    public static Product decreaseStock(Product product, int quantity) {
        return product.decreaseStock(quantity);
    }

    public static Product updatePrice(Product product, Money newPrice) {
        return product.updatePrice(newPrice);
    }
}

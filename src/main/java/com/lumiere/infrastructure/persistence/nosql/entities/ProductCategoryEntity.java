package com.lumiere.infrastructure.persistence.nosql.entities;

import com.lumiere.domain.entities.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("product_categories")
public class ProductCategoryEntity {

    @Id
    private UUID id;

    @Indexed
    private String categoryName;

    @Indexed
    private String subcategoryName;

    private com.lumiere.domain.entities.CategoryJson categoryJson;

    public ProductCategoryEntity(ProductCategory domainProductCategory) {
        this.id = domainProductCategory.getId();
        this.categoryJson = domainProductCategory.getCategory();
        this.categoryName = domainProductCategory.getCategory().getName().name();
        this.subcategoryName = domainProductCategory.getCategory().getSubcategory().name();
    }

    public ProductCategory toDomain() {
        return new ProductCategory(
                this.id,
                this.categoryJson.getName(),
                this.categoryJson.getSubcategory());
    }
}
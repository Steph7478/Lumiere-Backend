package com.lumiere.infrastructure.persistence.nosql.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lumiere.domain.entities.CategoryJson;
import com.lumiere.domain.entities.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("product_categories")
public class ProductCategoryEntity {

    @Id
    private UUID id;

    @JsonProperty("category")
    private CategoryJson categoryJson;

    public ProductCategoryEntity(ProductCategory domainProductCategory) {
        this.id = domainProductCategory.getId();
        this.categoryJson = domainProductCategory.getCategory();
    }

    public ProductCategory toDomain() {
        return new ProductCategory(
                this.id,
                this.categoryJson.getName(),
                this.categoryJson.getSubcategory());
    }
}

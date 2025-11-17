package com.lumiere.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

import java.util.Collections;

@Component
public final class ProductMapper implements BaseMapper<Product, ProductJpaEntity> {

    @Override
    public ProductJpaEntity toJpa(Product domain) {
        return new ProductJpaEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getPrice().getAmount(),
                domain.getPrice().getCurrency().name(),
                domain.getStock().getQuantity());
    }

    @Override
    public Product toDomain(ProductJpaEntity jpaEntity) {
        Money price = new Money(
                jpaEntity.getPriceAmount(),
                CurrencyType.valueOf(jpaEntity.getPriceCurrency()));
        Stock stock = new Stock(jpaEntity.getStockQuantity());

        return Product.from(
                jpaEntity.getId(),
                jpaEntity.getName(),
                jpaEntity.getDescription(),
                price,
                stock);
    }

    public ProductDetailReadModel toReadModel(
            ProductJpaEntity jpaEntity,
            ProductCategory nosqlCategory) {
        Money price = new Money(
                jpaEntity.getPriceAmount(),
                CurrencyType.valueOf(jpaEntity.getPriceCurrency()));

        final var category = nosqlCategory != null ? nosqlCategory.getCategory() : null;
        final var subCategory = nosqlCategory != null ? nosqlCategory.getSubcategory() : null;

        return new ProductDetailReadModel(
                jpaEntity.getId() != null ? jpaEntity.getId().toString() : null,
                jpaEntity.getName(),
                jpaEntity.getDescription(),
                price,
                Collections.emptyList(),
                jpaEntity.getStockQuantity(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdatedAt(),
                category,
                subCategory);
    }
}
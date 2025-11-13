package com.lumiere.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

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
        Money price = new Money(jpaEntity.getPriceAmount(), CurrencyType.valueOf(jpaEntity.getPriceCurrency()));
        Stock stock = new Stock(jpaEntity.getStockQuantity());
        return Product.createProduct(jpaEntity.getName(), jpaEntity.getDescription(), price, stock);
    }

}

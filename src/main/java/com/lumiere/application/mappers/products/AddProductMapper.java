package com.lumiere.application.mappers.products;

import org.springframework.stereotype.Component;

import com.lumiere.application.dtos.product.command.add.AddProductInput;
import com.lumiere.application.mappers.base.BaseMapper;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.services.ProductService;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

@Component
public class AddProductMapper implements BaseMapper<Product, AddProductInput> {

    @Override
    public AddProductInput toDTO(Product entity) {
        return new AddProductInput(
                entity.getName(),
                entity.getDescription(),
                entity.getPrice().getAmount(),
                entity.getPrice().getCurrency(),
                entity.getStock().getQuantity(),
                null,
                null);
    }

    @Override
    public Product toEntity(AddProductInput dto) {
        Money price = new Money(dto.priceAmount(), dto.currency());
        Stock stock = new Stock(dto.stockQuantity());
        return ProductService.createProduct(dto.name(), dto.description(), price, stock);
    }
}

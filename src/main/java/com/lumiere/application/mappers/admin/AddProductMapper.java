package com.lumiere.application.mappers.admin;

import java.util.UUID;
import org.springframework.stereotype.Component;

import com.lumiere.application.dtos.admin.command.add.AddProductInput;
import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.mappers.base.BaseMapper;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.enums.CategoriesEnum;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.services.ProductCategoryService;
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
                entity.getStock(),
                null,
                null);
    }

    @Override
    public Product toEntity(AddProductInput dto) {
        Money price = new Money(dto.priceAmount(), dto.currency());
        Stock stock = new Stock(dto.stockQuantity());
        return ProductService.createProduct(dto.name(), dto.description(), price, stock);
    }

    public ProductCategory toProductCategoryEntity(AddProductInput dto, UUID productId) {
        return ProductCategoryService.createProductCategory(productId, dto.category(), dto.subcategory());
    }

    public AddProductOutput toOutputDTO(Product product, CategoriesEnum.Category category,
            CategoriesEnum.SubCategory subCategory) {

        ProductDetailReadModel readModel = new ProductDetailReadModel(
                product.getId().toString(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getRatings(),
                product.getStock(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                category,
                subCategory);

        return new AddProductOutput(readModel);
    }
}
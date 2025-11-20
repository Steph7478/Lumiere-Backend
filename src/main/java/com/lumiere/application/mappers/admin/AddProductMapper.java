package com.lumiere.application.mappers.admin;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

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

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddProductMapper extends BaseMapper<Product, AddProductInput> {

    @Mapping(target = "priceAmount", source = "price.amount")
    @Mapping(target = "currency", source = "price.currency")
    @Mapping(target = "stockQuantity", source = "stock.quantity")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subcategory", ignore = true)
    AddProductInput toDTO(Product entity);

    default Money mapToMoney(AddProductInput dto) {
        return new Money(dto.priceAmount(), dto.currency());
    }

    default Stock mapToStock(AddProductInput dto) {
        return new Stock(dto.stockQuantity());
    }

    default Product toEntity(AddProductInput dto) {
        Money price = mapToMoney(dto);
        Stock stock = mapToStock(dto);
        return ProductService.createProduct(dto.name(), dto.description(), price, stock);
    }

    default ProductCategory toProductCategoryEntity(UUID productId, AddProductInput dto) {
        return ProductCategoryService.createProductCategory(productId, dto.category(), dto.subcategory());
    }

    default AddProductOutput toOutputDTO(Product product, CategoriesEnum.Category category,
            CategoriesEnum.SubCategory subCategory) {

        ProductDetailReadModel readModel = new ProductDetailReadModel(
                product.getId().toString(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getRatings(),
                product.getStock().getQuantity(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                category,
                subCategory);

        return new AddProductOutput(readModel);
    }
}
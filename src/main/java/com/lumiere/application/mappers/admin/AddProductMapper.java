package com.lumiere.application.mappers.admin;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.mapstruct.Mapper;

import com.lumiere.application.dtos.admin.command.add.AddProductInput;
import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.dtos.admin.command.add.AddProductRequestData;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.enums.CategoriesEnum;
import com.lumiere.domain.factories.ProductFactory;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.services.ProductCategoryService;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

@Mapper(componentModel = "spring")
public interface AddProductMapper {

    default Product toEntity(AddProductRequestData dto) {
        return ProductFactory.create(
                dto.name(),
                dto.description(),
                new Money(dto.priceAmount(), dto.currency()),
                new Stock(dto.stockQuantity()));
    }

    default List<Product> toEntities(AddProductInput input) {
        return input.items().stream()
                .map(this::toEntity)
                .toList();
    }

    default ProductCategory toProductCategoryEntity(UUID productId, AddProductRequestData dto) {
        return ProductCategoryService.createProductCategory(
                productId,
                dto.category(),
                dto.subcategory());
    }

    default ProductDetailReadModel toReadModel(
            Product product,
            CategoriesEnum.Category category,
            CategoriesEnum.SubCategory subCategory) {

        return new ProductDetailReadModel(
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
    }

    default List<ProductDetailReadModel> toReadModels(
            List<Product> products,
            List<AddProductRequestData> items) {

        return IntStream.range(0, products.size())
                .mapToObj(i -> toReadModel(
                        products.get(i),
                        items.get(i).category(),
                        items.get(i).subcategory()))
                .toList();
    }

    default AddProductOutput toOutputDTO(List<ProductDetailReadModel> models) {
        return new AddProductOutput(models);
    }
}

package com.lumiere.application.mappers.admin;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.dtos.admin.command.add.AddProductRequestData;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.readmodels.ProductDetailReadModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddProductMapper {
    @Mapping(target = "id", expression = "java(product.getId().toString())")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "ratings", source = "product.ratings")
    @Mapping(target = "stock", expression = "java(product.getStock().getQuantity())")
    @Mapping(target = "createdAt", source = "product.createdAt")
    @Mapping(target = "updatedAt", source = "product.updatedAt")
    @Mapping(target = "category", source = "dto.category")
    @Mapping(target = "subCategory", source = "dto.subcategory")
    ProductDetailReadModel toReadModel(Product product, AddProductRequestData dto);

    default AddProductOutput toOutput(List<ProductDetailReadModel> models) {
        return new AddProductOutput(models);
    }
}

package com.lumiere.application.usecases.admin;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.admin.command.add.AddProductInput;
import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.interfaces.admin.IAddProductUseCase;
import com.lumiere.application.mappers.admin.AddProductMapper;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.shared.annotations.validators.RequireAdmin;

import jakarta.transaction.Transactional;

@Service
public class AddProductUseCase implements IAddProductUseCase {

    private final ProductRepository productRepository;
    private final NoSqlRepository<ProductCategory> categoryRepository;
    private final AddProductMapper addProductMapper;

    public AddProductUseCase(
            ProductRepository productRepository,
            NoSqlRepository<ProductCategory> categoryRepository,
            AddProductMapper addProductMapper) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.addProductMapper = addProductMapper;
    }

    @Override
    @Transactional
    @RequireAdmin
    public AddProductOutput execute(AddProductInput input) {

        List<Product> products = addProductMapper.toEntities(input);
        products.forEach(productRepository::save);

        List<ProductCategory> categories = IntStream.range(0, products.size())
                .mapToObj(i -> addProductMapper.toProductCategoryEntity(
                        products.get(i).getId(),
                        input.items().get(i)))
                .toList();

        categories.forEach(categoryRepository::save);

        return addProductMapper.toOutputDTO(
                addProductMapper.toReadModels(products, input.items()));
    }
}

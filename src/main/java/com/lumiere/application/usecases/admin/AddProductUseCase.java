package com.lumiere.application.usecases.admin;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.admin.command.add.AddProductInput;
import com.lumiere.application.dtos.admin.command.add.AddProductOutput;
import com.lumiere.application.interfaces.admin.IAddProductUseCase;
import com.lumiere.application.mappers.admin.AddProductMapper;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.shared.annotations.validators.requireAdmin.RequireAdmin;

import jakarta.transaction.Transactional;

@Service
public class AddProductUseCase implements IAddProductUseCase {

    private final ProductRepository productRepository;
    private final NoSqlRepository<ProductCategory> categoryRepository;
    private final AddProductMapper addProductMapper;

    public AddProductUseCase(
            ProductRepository productRepository,
            NoSqlRepository<ProductCategory> categoryRepository, AddProductMapper addProductMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.addProductMapper = addProductMapper;
    }

    @Override
    @Transactional
    @RequireAdmin
    public AddProductOutput execute(AddProductInput input) {

        Product product = addProductMapper.toEntity(input);
        Product savedProduct = productRepository.save(product);

        ProductCategory category = addProductMapper.toProductCategoryEntity(input, savedProduct.getId());
        categoryRepository.save(category);

        return new AddProductOutput(savedProduct, category.getCategory(), category.getSubcategory());
    }

}

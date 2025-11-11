package com.lumiere.application.usecases.products;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.product.command.add.AddProductInput;
import com.lumiere.application.dtos.product.command.add.output.AddProductOutput;
import com.lumiere.application.interfaces.products.IAddProductUseCase;
import com.lumiere.application.mappers.products.AddProductMapper;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.domain.services.ProductCategoryService;
import com.lumiere.domain.services.ProductService;

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
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE_PRODUCT')")
    public AddProductOutput execute(AddProductInput input) {
        Product entity = addProductMapper.toEntity(input);

        Product product = ProductService.createProduct(
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock());

        Product savedProduct = productRepository.save(product);

        ProductCategory category = ProductCategoryService.createProductCategory(
                savedProduct.getId(),
                input.category(),
                input.subcategory());

        categoryRepository.save(category);

        return new AddProductOutput(product, category);
    }
}

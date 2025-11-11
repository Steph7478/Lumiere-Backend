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
        Product product = addProductMapper.toEntity(input);
        Product savedProduct = productRepository.save(product);

        ProductCategory category = addProductMapper.toProductCategoryEntity(input, savedProduct.getId());

        categoryRepository.save(category);

        return new AddProductOutput(savedProduct, category);
    }

}

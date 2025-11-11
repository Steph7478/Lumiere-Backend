package com.lumiere.application.usecases.products;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.product.command.add.AddProductInput;
import com.lumiere.application.dtos.product.command.add.output.AddProductOutput;
import com.lumiere.application.interfaces.products.IAddProductUseCase;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.domain.services.ProductCategoryService;
import com.lumiere.domain.services.ProductService;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;

import jakarta.transaction.Transactional;

@Service
public class AddProductUseCase implements IAddProductUseCase {

    private final ProductRepository productRepository;
    private final NoSqlRepository<ProductCategory> categoryRepository;

    public AddProductUseCase(
            ProductRepository productRepository,
            NoSqlRepository<ProductCategory> categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE_PRODUCT')")
    public AddProductOutput execute(AddProductInput input) {
        Money price = new Money(input.priceAmount(), input.currency());
        Stock stock = new Stock(input.stockQuantity());

        Product product = ProductService.createProduct(
                input.name(),
                input.description(),
                price,
                stock);

        Product savedProduct = productRepository.save(product);

        ProductCategory category = ProductCategoryService.createProductCategory(
                savedProduct.getId(),
                input.category(),
                input.subcategory());

        categoryRepository.save(category);

        return new AddProductOutput(product, category);
    }
}

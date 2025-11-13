package com.lumiere.application.usecases.admin;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockInput;
import com.lumiere.application.dtos.admin.command.stock.decrease.DecreaseStockOutput;
import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.application.interfaces.admin.IDecreaseStockUseCase;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.domain.services.ProductService;
import com.lumiere.shared.annotations.validators.requireAdmin.RequireAdmin;

@Service
public class DecreaseStockUseCase implements IDecreaseStockUseCase {
    private final ProductRepository productRepository;

    public DecreaseStockUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @RequireAdmin
    public DecreaseStockOutput execute(DecreaseStockInput input) {
        Product product = productRepository.findById(input.id())
                .orElseThrow(() -> new ProductNotFoundException(input.id()));

        ProductService.increaseStock(product, input.quantity());

        productRepository.save(product);

        return new DecreaseStockOutput(product.getId(), product.getStock());
    }

}

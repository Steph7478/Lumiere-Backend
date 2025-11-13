package com.lumiere.application.usecases.admin;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockInput;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockOutput;
import com.lumiere.application.dtos.admin.command.stock.increase.IncreaseStockRequestData;
import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.application.interfaces.admin.IIncreaseStockUseCase;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.domain.services.ProductService;
import com.lumiere.shared.annotations.validators.requireAdmin.RequireAdmin;

@Service
public class IncreaseStockUseCase implements IIncreaseStockUseCase {
    private final ProductRepository productRepository;

    public IncreaseStockUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @RequireAdmin
    public IncreaseStockOutput execute(IncreaseStockInput input) {
        IncreaseStockRequestData requestData = input.requestData();

        Product product = productRepository.findById(input.id())
                .orElseThrow(() -> new ProductNotFoundException(input.id()));

        ProductService.increaseStock(product, requestData.quantity());

        productRepository.update(product);

        return new IncreaseStockOutput(product.getId(), product.getStock().getQuantity());
    }

}

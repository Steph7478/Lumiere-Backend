package com.lumiere.application.usecases.admin;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.admin.command.modify.ModifyProductInput;
import com.lumiere.application.dtos.admin.command.modify.ModifyProductOutput;
import com.lumiere.application.dtos.admin.command.modify.ModifyProductRequestData;
import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.application.interfaces.admin.IModifyProductUseCase;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.domain.services.ProductService;
import com.lumiere.shared.annotations.validators.requireAdmin.RequireAdmin;

@Service
public class ModifyProductUseCase implements IModifyProductUseCase {
    private final ProductRepository productRepository;

    public ModifyProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @RequireAdmin
    public ModifyProductOutput execute(ModifyProductInput input) {
        ModifyProductRequestData request = input.requestData();

        Product product = productRepository.findById(input.id())
                .orElseThrow(() -> new ProductNotFoundException(input.id()));

        ProductService.update(product, request.name(), request.description());

        productRepository.update(product);

        return new ModifyProductOutput(product.getId(), product.getName(), product.getDescription());
    }

}

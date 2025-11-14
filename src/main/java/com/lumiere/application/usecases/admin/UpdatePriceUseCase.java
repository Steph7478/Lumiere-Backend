package com.lumiere.application.usecases.admin;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.admin.command.price.UpdatePriceInput;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceOutput;
import com.lumiere.application.dtos.admin.command.price.UpdatePriceRequestData;
import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.application.interfaces.admin.IUpdatePriceUseCase;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.domain.services.ProductService;
import com.lumiere.domain.vo.Money;
import com.lumiere.shared.annotations.validators.requireAdmin.RequireAdmin;

@Service
public class UpdatePriceUseCase implements IUpdatePriceUseCase {
    private final ProductRepository productRepository;

    public UpdatePriceUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @RequireAdmin
    public UpdatePriceOutput execute(UpdatePriceInput input) {
        UpdatePriceRequestData requestData = input.requestData();

        Product product = productRepository.findById(input.id())
                .orElseThrow(() -> new ProductNotFoundException(input.id()));

        Money price = new Money(requestData.priceAmount().orElse(product.getPrice().getAmount()),
                requestData.currency().orElse(product.getPrice().getCurrency()));
        Product updatedProduct = ProductService.updatePrice(product, price);

        productRepository.update(updatedProduct);

        return new UpdatePriceOutput(updatedProduct.getId(), updatedProduct.getPrice());
    }

}

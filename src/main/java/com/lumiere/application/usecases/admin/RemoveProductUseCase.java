package com.lumiere.application.usecases.admin;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lumiere.application.dtos.admin.command.remove.RemoveProductInput;
import com.lumiere.application.dtos.admin.command.remove.RemoveProductOutput;
import com.lumiere.application.interfaces.admin.IRemoveProductUseCase;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.repositories.ProductRepository;
import com.lumiere.infrastructure.storage.S3StorageService;

@Service
public class RemoveProductUseCase implements IRemoveProductUseCase {
    private static final String PRODUCT_BUCKET_NAME = "product-pictures";

    private ProductRepository productRepo;
    private S3StorageService storage;

    protected RemoveProductUseCase(ProductRepository productRepo, S3StorageService storage) {
        this.productRepo = productRepo;
        this.storage = storage;
    }

    @Override
    public RemoveProductOutput execute(RemoveProductInput input) {
        List<Product> products = productRepo.findAllByIdIn(input.productsId());

        List<String> keysToDelete = products.stream()
                .map(Product::getImageUrl)
                .map(url -> url.substring(url.lastIndexOf('/') + 1))
                .toList();

        storage.deleteObjectsByKeys(PRODUCT_BUCKET_NAME, keysToDelete);

        List<UUID> productIds = products.stream()
                .map(Product::getId)
                .toList();
        productRepo.deleteAllById(productIds);

        return new RemoveProductOutput();
    }

}

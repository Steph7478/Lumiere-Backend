package com.lumiere.application.services;

import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.repositories.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductCacheService {

    private final ProductRepository productRepo;

    public ProductCacheService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Map<UUID, Product> loadProductCache(Set<UUID> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return Map.of();
        }

        List<Product> products = productRepo.findAllByIdIn(productIds);

        Map<UUID, Product> productCache = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        if (productCache.size() != productIds.size()) {
            UUID missingId = productIds.stream()
                    .filter(id -> !productCache.containsKey(id))
                    .findFirst().orElseThrow(IllegalArgumentException::new);

            throw new ProductNotFoundException(missingId);
        }

        return productCache;
    }
}
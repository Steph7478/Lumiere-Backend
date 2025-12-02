package com.lumiere.application.services;

import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.application.ports.ProductDetailReadPort;
import com.lumiere.domain.readmodels.ProductDetailReadModel;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductCacheService {

    private final ProductDetailReadPort productRepo;

    public ProductCacheService(ProductDetailReadPort productRepo) {
        this.productRepo = productRepo;
    }

    @Cacheable(value = "product_list", keyGenerator = "sortedSetKeyGenerator", unless = "#result == null", condition = "#productIds != null && !#productIds.isEmpty()")
    public Map<String, ProductDetailReadModel> loadProductCache(Set<String> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return Map.of();
        }

        Set<java.util.UUID> uuidSet = productIds.stream()
                .map(java.util.UUID::fromString)
                .collect(Collectors.toSet());

        List<ProductDetailReadModel> products = productRepo.findAllProductsById(uuidSet);

        Map<String, ProductDetailReadModel> productCache = products.stream()
                .collect(Collectors.toMap(p -> p.id().toString(), p -> p));

        if (productCache.size() != productIds.size()) {
            String missingId = productIds.stream()
                    .filter(id -> !productCache.containsKey(id))
                    .findFirst()
                    .orElseThrow();

            throw new ProductNotFoundException(java.util.UUID.fromString(missingId));
        }

        return productCache;
    }
}

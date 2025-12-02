package com.lumiere.application.services;

import com.lumiere.application.exceptions.product.ProductNotFoundException;
import com.lumiere.application.ports.ProductDetailReadPort;
import com.lumiere.domain.readmodels.ProductDetailReadModel;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductCacheService {

    private final ProductDetailReadPort productRepo;

    public ProductCacheService(ProductDetailReadPort productRepo) {
        this.productRepo = productRepo;
    }

    @Cacheable(value = "productJpaList", keyGenerator = "sortedSetKeyGenerator", unless = "#result == null", condition = "#productIds != null && !#productIds.isEmpty()")
    public Map<UUID, ProductDetailReadModel> loadProductCache(Set<UUID> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return Map.of();
        }

        List<ProductDetailReadModel> products = productRepo.findAllProductsById(productIds);

        Map<UUID, ProductDetailReadModel> productCache = products.stream()
                .collect(Collectors.toMap(ProductDetailReadModel::id, p -> p));

        if (productCache.size() != productIds.size()) {
            UUID missingId = productIds.stream()
                    .filter(id -> !productCache.containsKey(id))
                    .findFirst().orElseThrow(IllegalArgumentException::new);

            throw new ProductNotFoundException(missingId);
        }

        return productCache;
    }
}
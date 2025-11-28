package com.lumiere.application.services;

import com.lumiere.domain.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ItemMappingService {

    public <I, O> List<O> mapItemsToDomainVO(
            List<I> itemsRequest,
            Map<UUID, Product> productCache,
            Function<I, UUID> productIdGetter,
            BiFunction<Product, I, O> itemMapper) {

        return itemsRequest.stream()
                .map(itemRequestData -> {
                    UUID productId = productIdGetter.apply(itemRequestData);
                    Product product = productCache.get(productId);
                    return itemMapper.apply(product, itemRequestData);
                })
                .collect(Collectors.toList());
    }
}
package com.lumiere.application.services;

import com.lumiere.domain.readmodels.ProductDetailReadModel;

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
            Map<String, ProductDetailReadModel> productCache,
            Function<I, UUID> productIdGetter,
            BiFunction<ProductDetailReadModel, I, O> itemMapper) {

        return itemsRequest.stream()
                .map(itemRequestData -> {
                    UUID productId = productIdGetter.apply(itemRequestData);
                    ProductDetailReadModel productDetail = productCache.get(productId.toString());

                    return itemMapper.apply(productDetail, itemRequestData);
                })
                .collect(Collectors.toList());
    }
}
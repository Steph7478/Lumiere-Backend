package com.lumiere.application.dtos.product.query;

public record ProductFindAllCriteria(int page,
        int size,
        String sortBy) {

}

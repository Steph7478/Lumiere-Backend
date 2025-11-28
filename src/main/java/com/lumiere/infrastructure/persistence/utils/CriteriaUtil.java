package com.lumiere.infrastructure.persistence.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class CriteriaUtil {

    public static Pageable buildPageable(int page, int size, String sortBy) {
        return PageRequest.of(page, size, Sort.by(sortBy));
    }
}

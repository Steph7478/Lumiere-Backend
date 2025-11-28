package com.lumiere.infrastructure.persistence.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("null")
public abstract class PaginationConverter {

    public static <T, U> Page<U> convert(Page<T> sourcePage, Function<T, U> mapper) {
        List<U> mappedContent = sourcePage.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new PageImpl<>(
                mappedContent,
                sourcePage.getPageable(),
                sourcePage.getTotalElements());
    }

    public static <U> Page<U> emptyPage(Pageable pageable) {
        return new PageImpl<>(
                List.of(),
                pageable,
                0);
    }
}
package com.lumiere.infrastructure.persistence.jpa.utils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public final class CacheKeyGenerator {

    private CacheKeyGenerator() {
    }

    public static String listToSortedString(List<UUID> ids) {
        if (ids == null) {
            return "null";
        }
        return ids.stream()
                .filter(Objects::nonNull)
                .sorted()
                .map(UUID::toString)
                .collect(Collectors.joining(","));
    }
}
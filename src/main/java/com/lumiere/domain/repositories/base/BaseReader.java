package com.lumiere.domain.repositories.base;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseReader<T> {
    Optional<T> findById(UUID id);

    List<T> findAll();
}

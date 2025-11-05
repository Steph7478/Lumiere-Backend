package com.lumiere.infrastructure.repositories.base;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseReader<T> {

    Optional<T> findById(UUID id);

    Optional<T> findByIdWithEager(UUID id, String... relations);

    List<T> findAll();

    List<T> findAllWithEager(String... relations);

}

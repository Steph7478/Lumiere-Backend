package com.lumiere.domain.repositories.base;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.User;

public interface BaseReader<T> {
    Optional<T> findById(UUID id);

    Optional<T> findByIdWithRelations(UUID id, String... relations);

    List<T> findAll();

    List<User> findAllWithRelations();
}

package com.lumiere.domain.repositories.base;

import java.util.UUID;

public interface BaseWriter<T> {

    T save(T entity);

    void deleteById(UUID id);

    T update(T entity);
}

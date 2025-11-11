package com.lumiere.domain.repositories;

import java.util.List;
import java.util.UUID;

public interface NoSqlRepository<T> {

    void save(T entity);

    void delete(UUID id);

    T findById(UUID id);

    List<T> findBySubcategory(String subcategory);

    List<T> findByCategory(String category);
}
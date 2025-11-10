package com.lumiere.domain.repositories;

import java.util.List;

public interface NoSqlRepository<T> {

    void save(String id, T entity);

    void delete(String id);

    T findById(String id);

    List<T> findBySubcategory(String subcategory);

    List<T> findByCategory(String category);
}
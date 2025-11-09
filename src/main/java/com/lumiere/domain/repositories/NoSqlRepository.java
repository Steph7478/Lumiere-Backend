package com.lumiere.domain.repositories;

public interface NoSqlRepository<T> {
    void save(String id, T obj);

    T search(String id, Class<T> clazz);

    void delete(String id);
}

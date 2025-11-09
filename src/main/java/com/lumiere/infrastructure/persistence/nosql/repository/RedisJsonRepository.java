package com.lumiere.infrastructure.persistence.nosql.repository;

import com.lumiere.domain.repositories.NoSqlRepository;

import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisJsonRepository<T> implements NoSqlRepository<T> {

    private final RedisTemplate<String, T> redisTemplate;

    public RedisJsonRepository(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String id, T obj) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(obj);
        redisTemplate.opsForValue().set(id, obj);
    }

    @Override
    public T search(String id, Class<T> clazz) {
        Objects.requireNonNull(id);
        return redisTemplate.opsForValue().get(id);
    }

    @Override
    public void delete(String id) {
        Objects.requireNonNull(id);
        redisTemplate.delete(id);
    }
}

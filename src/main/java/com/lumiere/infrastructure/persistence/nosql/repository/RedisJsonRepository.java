package com.lumiere.infrastructure.persistence.nosql.repository;

import com.lumiere.domain.entities.ProductCategory;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisJsonRepository {

    private final RedisTemplate<String, ProductCategory> objectTemplate;
    private final RedisTemplate<String, String> stringTemplate;

    public RedisJsonRepository(
            RedisTemplate<String, ProductCategory> objectTemplate,
            RedisTemplate<String, String> stringTemplate) {
        this.objectTemplate = objectTemplate;
        this.stringTemplate = stringTemplate;
    }

    public void save(String id, ProductCategory obj) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(obj);
        objectTemplate.opsForValue().set(id, obj);
    }

    public ProductCategory search(String id) {
        Objects.requireNonNull(id);
        return objectTemplate.opsForValue().get(id);
    }

    public void delete(String id) {
        Objects.requireNonNull(id);
        objectTemplate.delete(id);
    }

    public void addToSet(String key, String member) {
        Objects.requireNonNull(key);
        stringTemplate.opsForSet().add(key, member);
    }

    public Set<String> getSetMembers(String key) {
        Objects.requireNonNull(key);
        Set<String> members = stringTemplate.opsForSet().members(key);
        return members != null ? members : Collections.emptySet();
    }
}

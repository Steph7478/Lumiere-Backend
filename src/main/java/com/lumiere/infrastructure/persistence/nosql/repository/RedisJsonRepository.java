package com.lumiere.infrastructure.persistence.nosql.repository;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RedisJsonRepository implements NoSqlRepository<ProductCategory> {

    private final RedisTemplate<String, ProductCategory> objectTemplate;
    private final RedisTemplate<String, String> stringTemplate;

    public RedisJsonRepository(RedisTemplate<String, ProductCategory> objectTemplate,
            RedisTemplate<String, String> stringTemplate) {
        this.objectTemplate = objectTemplate;
        this.stringTemplate = stringTemplate;
    }

    @Override
    public void save(String id, ProductCategory obj) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(obj);
        objectTemplate.opsForValue().set(id, obj);
        addToSet("subcategory:" + obj.getSubcategory(), id);
        addToSet("category:" + obj.getCategory(), obj.getSubcategory());
    }

    @Override
    public ProductCategory findById(String id) {
        Objects.requireNonNull(id);
        return Objects.requireNonNull(objectTemplate.opsForValue().get(id));
    }

    @Override
    public List<ProductCategory> findBySubcategory(String subcategory) {
        Set<String> ids = getSetMembers("subcategory:" + subcategory);
        Objects.requireNonNull(ids);
        List<ProductCategory> results = objectTemplate.opsForValue().multiGet(ids);
        return results != null ? results.stream().filter(Objects::nonNull).collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public List<ProductCategory> findByCategory(String category) {
        Set<String> subcategories = getSetMembers("category:" + category);
        Set<String> allIds = subcategories.stream()
                .flatMap(sub -> getSetMembers("subcategory:" + sub).stream())
                .collect(Collectors.toSet());

        Objects.requireNonNull(allIds);
        List<ProductCategory> results = objectTemplate.opsForValue().multiGet(allIds);

        return results != null ? results.stream().filter(Objects::nonNull).collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public void delete(String id) {
        Objects.requireNonNull(id);
        ProductCategory obj = objectTemplate.opsForValue().get(id);

        if (obj != null) {
            removeFromSet("subcategory:" + obj.getSubcategory(), id);
            if (getSetMembers("subcategory:" + obj.getSubcategory()).isEmpty()) {
                removeFromSet("category:" + obj.getCategory(), obj.getSubcategory());
            }
        }
        objectTemplate.delete(id);
    }

    private void addToSet(String key, String member) {
        Objects.requireNonNull(key);
        stringTemplate.opsForSet().add(key, member);
    }

    private void removeFromSet(String key, String member) {
        Objects.requireNonNull(key);
        stringTemplate.opsForSet().remove(key, member);
    }

    private Set<String> getSetMembers(String key) {
        Objects.requireNonNull(key);
        Set<String> members = stringTemplate.opsForSet().members(key);
        return members != null ? members : Collections.emptySet();
    }
}
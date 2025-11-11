package com.lumiere.infrastructure.persistence.nosql.repository;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RedisJsonRepository implements NoSqlRepository<ProductCategory> {

    private final RedisTemplate<String, ProductCategory> objectTemplate;
    private final RedisTemplate<String, String> stringTemplate;
    private final RedisTemplate<String, UUID> uuidTemplate;

    public RedisJsonRepository(
            @Qualifier("productCategoryRedisTemplate") RedisTemplate<String, ProductCategory> objectTemplate,
            @Qualifier("customStringRedisTemplate") RedisTemplate<String, String> stringRedisTemplate,
            @Qualifier("uuidRedisTemplate") RedisTemplate<String, UUID> uuidRedisTemplate) {
        this.objectTemplate = objectTemplate;
        this.stringTemplate = stringRedisTemplate;
        this.uuidTemplate = uuidRedisTemplate;
    }

    @Override
    public void save(ProductCategory obj) {
        String key = obj.getId().toString();
        Objects.requireNonNull(key);

        String subcategoryString = obj.getSubcategory().toString();
        String categoryString = obj.getCategory().toString();

        objectTemplate.opsForValue().set(key, obj);

        uuidTemplate.opsForSet().add("subcategory:" + subcategoryString, obj.getId());
        stringTemplate.opsForSet().add("category:" + categoryString, subcategoryString);
    }

    @Override
    public ProductCategory findById(UUID id) {
        ProductCategory result = objectTemplate.opsForValue().get(Objects.requireNonNull(id.toString()));

        return Objects.requireNonNull(result, "ProductCategory not found for ID: " + id);
    }

    @Override
    public List<ProductCategory> findBySubcategory(String subcategory) {
        Set<UUID> uuidSet = uuidTemplate.opsForSet().members("subcategory:" + subcategory);

        if (uuidSet == null || uuidSet.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> ids = uuidSet.stream().map(UUID::toString).collect(Collectors.toSet());
        Objects.requireNonNull(ids);

        List<ProductCategory> results = objectTemplate.opsForValue().multiGet(ids);

        return results != null ? results.stream().filter(Objects::nonNull).collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public List<ProductCategory> findByCategory(String category) {

        Set<String> subcategories = stringTemplate.opsForSet().members("category:" + category);
        Objects.requireNonNull(subcategories);

        Set<String> allIds = subcategories.stream()
                .flatMap(sub -> getUuidSetMembersAsString("subcategory:" + sub).stream())
                .collect(Collectors.toSet());

        if (allIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductCategory> results = objectTemplate.opsForValue().multiGet(allIds);

        return results != null ? results.stream().filter(Objects::nonNull).collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public void delete(UUID id) {
        String key = id.toString();
        Objects.requireNonNull(key);

        ProductCategory obj = objectTemplate.opsForValue().get(key);

        if (obj != null) {
            String subcategoryString = obj.getSubcategory().toString();
            String categoryString = obj.getCategory().toString();

            uuidTemplate.opsForSet().remove("subcategory:" + subcategoryString, id);

            if (uuidTemplate.opsForSet().size("subcategory:" + subcategoryString) == 0) {
                stringTemplate.opsForSet().remove("category:" + categoryString, subcategoryString);
            }
        }
        objectTemplate.delete(key);
    }

    private Set<String> getUuidSetMembersAsString(String key) {
        Objects.requireNonNull(key);
        Set<UUID> uuidMembers = uuidTemplate.opsForSet().members(key);
        return uuidMembers != null ? uuidMembers.stream().map(UUID::toString).collect(Collectors.toSet())
                : Collections.emptySet();
    }
}
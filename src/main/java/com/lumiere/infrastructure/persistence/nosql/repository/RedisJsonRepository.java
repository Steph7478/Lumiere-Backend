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
    private final RedisTemplate<String, Object> genericTemplate;

    public RedisJsonRepository(
            @Qualifier("productCategoryRedisTemplate") RedisTemplate<String, ProductCategory> objectTemplate,
            @Qualifier("customStringRedisTemplate") RedisTemplate<String, String> stringRedisTemplate,
            @Qualifier("genericObjectTemplate") RedisTemplate<String, Object> genericTemplate) {
        this.objectTemplate = objectTemplate;
        this.stringTemplate = stringRedisTemplate;
        this.genericTemplate = genericTemplate;
    }

    @Override
    public void save(ProductCategory obj) {
        String key = obj.getId().toString();
        Objects.requireNonNull(key);

        String subcategoryString = obj.getSubcategory().toString();
        String categoryString = obj.getCategory().toString();

        objectTemplate.opsForValue().set(key, obj);
        genericTemplate.opsForSet().add("subcategory:" + subcategoryString, obj.getId());
        stringTemplate.opsForSet().add("category:" + categoryString, subcategoryString);
    }

    @Override
    public ProductCategory findById(UUID id) {
        String key = Objects.requireNonNull(id.toString());
        ProductCategory result = objectTemplate.opsForValue().get(key);

        return Objects.requireNonNull(result, "ProductCategory not found for ID: " + id);
    }

    @Override
    public List<ProductCategory> findBySubcategory(String subcategory) {
        Set<Object> objSet = genericTemplate.opsForSet().members("subcategory:" + subcategory);

        if (objSet == null || objSet.isEmpty())
            return Collections.emptyList();

        Set<String> ids = objSet.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toSet());

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

        if (allIds.isEmpty())
            return Collections.emptyList();

        List<ProductCategory> results = objectTemplate.opsForValue().multiGet(allIds);

        return results != null ? results.stream().filter(Objects::nonNull).collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public void delete(UUID id) {
        String key = id.toString();
        Objects.requireNonNull(key);

        ProductCategory obj = objectTemplate.opsForValue().get(key);
        Objects.requireNonNull(obj, "ProductCategory to delete not found for ID: " + id);

        String subcategoryString = obj.getSubcategory().toString();
        String categoryString = obj.getCategory().toString();

        genericTemplate.opsForSet().remove("subcategory:" + subcategoryString, id);

        Long size = genericTemplate.opsForSet().size("subcategory:" + subcategoryString);
        long subcategorySize = Objects.requireNonNullElse(size, 0L);

        if (subcategorySize == 0)
            stringTemplate.opsForSet().remove("category:" + categoryString, subcategoryString);

        objectTemplate.delete(key);
    }

    private Set<String> getUuidSetMembersAsString(String key) {
        Objects.requireNonNull(key);
        Set<Object> objMembers = genericTemplate.opsForSet().members(key);

        return objMembers != null ? objMembers.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toSet())
                : Collections.emptySet();
    }
}
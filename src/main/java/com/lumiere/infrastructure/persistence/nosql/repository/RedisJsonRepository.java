package com.lumiere.infrastructure.persistence.nosql.repository;

import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RedisJsonRepository implements NoSqlRepository<ProductCategory> {

    private final RedisTemplate<String, Object> genericJsonRedisTemplate;
    private final ValueOperations<String, Object> valueOperations;
    private final ObjectMapper objectMapper;

    public RedisJsonRepository(
            @Qualifier("genericJsonRedisTemplate") RedisTemplate<String, Object> genericJsonRedisTemplate,
            ObjectMapper objectMapper) {
        this.genericJsonRedisTemplate = genericJsonRedisTemplate;
        this.valueOperations = genericJsonRedisTemplate.opsForValue();
        this.objectMapper = objectMapper;
    }

    private ProductCategory convertToProductCategory(Object item) {
        if (item == null) {
            return null;
        }
        return objectMapper.convertValue(item, ProductCategory.class);
    }

    @Override
    public void save(ProductCategory obj) {
        String key = obj.getId().toString();
        Objects.requireNonNull(key);

        String subcategoryString = obj.getSubcategory().toString();
        String categoryString = obj.getCategory().toString();
        valueOperations.set(key, obj);

        genericJsonRedisTemplate.opsForSet().add("subcategory:" + subcategoryString, obj.getId());
        genericJsonRedisTemplate.opsForSet().add("category:" + categoryString, subcategoryString);
    }

    @Override
    public void delete(UUID id) {
        String key = id.toString();
        Objects.requireNonNull(key);
        ProductCategory obj = findById(id);

        String subcategoryString = obj.getSubcategory().toString();
        String categoryString = obj.getCategory().toString();

        genericJsonRedisTemplate.opsForSet().remove("subcategory:" + subcategoryString, id);
        Long size = genericJsonRedisTemplate.opsForSet().size("subcategory:" + subcategoryString);

        long subcategorySize = Objects.requireNonNullElse(size, 0L);
        if (subcategorySize == 0)
            genericJsonRedisTemplate.opsForSet().remove("category:" + categoryString, subcategoryString);

        genericJsonRedisTemplate.delete(key);
    }

    @Override
    public ProductCategory findById(UUID id) {
        String key = Objects.requireNonNull(id.toString());

        Object result = valueOperations.get(key);
        if (result == null)
            throw new NoSuchElementException("ProductCategory not found for ID: " + id);

        return convertToProductCategory(result);
    }

    @Override
    public List<ProductCategory> findBySubcategory(String subcategory) {
        Set<Object> objSet = genericJsonRedisTemplate.opsForSet().members("subcategory:" + subcategory);

        if (objSet == null || objSet.isEmpty())
            return Collections.emptyList();

        Set<String> ids = objSet.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toSet());

        Objects.requireNonNull(ids);
        List<Object> results = valueOperations.multiGet(ids);

        return results != null ? results.stream()
                .filter(Objects::nonNull)
                .map(this::convertToProductCategory)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public List<ProductCategory> findByCategory(String category) {
        Set<Object> subcategoryObjects = genericJsonRedisTemplate.opsForSet().members("category:" + category);

        Set<String> subcategories = subcategoryObjects != null ? subcategoryObjects.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toSet()) : Collections.emptySet();

        if (subcategories.isEmpty())
            return Collections.emptyList();

        Set<String> allIds = subcategories.stream()
                .flatMap(sub -> getUuidSetMembersAsString("subcategory:" + sub).stream())
                .collect(Collectors.toSet());

        if (allIds.isEmpty())
            return Collections.emptyList();

        List<Object> results = valueOperations.multiGet(allIds);

        return results != null ? results.stream()
                .filter(Objects::nonNull)
                .map(this::convertToProductCategory)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public List<ProductCategory> findByIds(List<UUID> ids) {
        List<String> keys = ids.stream()
                .filter(Objects::nonNull)
                .map(UUID::toString)
                .collect(Collectors.toList());

        if (keys.isEmpty())
            return Collections.emptyList();

        List<Object> results = valueOperations.multiGet(keys);

        return results != null ? results.stream()
                .filter(Objects::nonNull)
                .map(this::convertToProductCategory)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private Set<String> getUuidSetMembersAsString(String key) {
        Objects.requireNonNull(key);
        Set<Object> objMembers = genericJsonRedisTemplate.opsForSet().members(key);

        return objMembers != null ? objMembers.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.toSet())
                : Collections.emptySet();
    }
}
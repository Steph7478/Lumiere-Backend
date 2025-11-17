package com.lumiere.infrastructure.persistence.adapters;

import com.lumiere.application.dtos.product.query.ProductDetailReadPort;
import com.lumiere.application.dtos.product.query.ProductSearchCriteria;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.lumiere.infrastructure.mappers.ProductMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductDetailReadAdapter implements ProductDetailReadPort {

    private final ProductJpaRepository sqlRepository;
    private final NoSqlRepository<ProductCategory> nosqlRepository;
    private final ProductMapper productDetailMapper;

    private record CategoryFilterResult(List<UUID> filteredProductIds, List<ProductCategory> categories) {
    }

    public ProductDetailReadAdapter(
            ProductJpaRepository sqlRepository,
            NoSqlRepository<ProductCategory> nosqlRepository,
            ProductMapper productDetailMapper) {
        this.sqlRepository = Objects.requireNonNull(sqlRepository);
        this.nosqlRepository = Objects.requireNonNull(nosqlRepository);
        this.productDetailMapper = Objects.requireNonNull(productDetailMapper);
    }

    @Override
    public Page<ProductDetailReadModel> findProductsByCriteria(ProductSearchCriteria criteria) {
        CategoryFilterResult filterResult = getFilteredProductIdsAndCategories(criteria);

        Specification<ProductJpaEntity> spec = buildSqlSpecification(criteria, filterResult.filteredProductIds());
        Pageable pageable = PageRequest.of(criteria.page(), criteria.size(), Sort.by(criteria.sortBy()));
        Page<ProductJpaEntity> productPage = sqlRepository.findAll(spec, pageable);

        List<UUID> productIdsOnPage = productPage.getContent().stream()
                .map(ProductJpaEntity::getId).toList();

        List<ProductCategory> categoriesToMap = nosqlRepository.findByIds(productIdsOnPage);
        Map<UUID, ProductCategory> categoryDataMap = categoriesToMap.stream()
                .collect(Collectors.toMap(ProductCategory::getId, Function.identity()));

        List<ProductDetailReadModel> readModels = productPage.getContent().stream()
                .map(jpaEntity -> productDetailMapper.toReadModel(jpaEntity, categoryDataMap.get(jpaEntity.getId())))
                .toList();

        Objects.requireNonNull(readModels);

        return new PageImpl<>(readModels, pageable, productPage.getTotalElements());
    }

    private CategoryFilterResult getFilteredProductIdsAndCategories(ProductSearchCriteria criteria) {
        if (criteria.category() == null && criteria.subCategory() == null)
            return new CategoryFilterResult(null, null);

        List<ProductCategory> categories;
        boolean hasCategory = criteria.category() != null;
        boolean hasSubCategory = criteria.subCategory() != null;

        if (hasCategory && hasSubCategory) {
            categories = nosqlRepository.findByCategoryAndSubcategory(
                    criteria.category().name(), criteria.subCategory().name());
        } else if (hasSubCategory) {
            categories = nosqlRepository.findBySubcategory(criteria.subCategory().name());
        } else {
            categories = nosqlRepository.findByCategory(criteria.category().name());
        }

        List<UUID> filteredProductIds = (categories != null)
                ? categories.stream().map(ProductCategory::getId).toList()
                : null;

        return new CategoryFilterResult(filteredProductIds, categories);
    }

    private Specification<ProductJpaEntity> buildSqlSpecification(
            ProductSearchCriteria criteria,
            List<UUID> includedIds) {

        List<Specification<ProductJpaEntity>> specifications = new ArrayList<>();

        if (criteria.name() != null && !criteria.name().isBlank()) {
            String searchName = "%" + criteria.name().toLowerCase() + "%";
            specifications.add((root, query, cb) -> cb.like(cb.lower(root.get("name")), searchName));
        }

        if (criteria.priceMin() != null)
            specifications
                    .add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("priceAmount"), criteria.priceMin()));

        if (criteria.priceMax() != null)
            specifications.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("priceAmount"), criteria.priceMax()));

        if (includedIds != null && !includedIds.isEmpty())
            specifications.add((root, query, cb) -> root.get("id").in(includedIds));

        return Specification.allOf(specifications);
    }
}
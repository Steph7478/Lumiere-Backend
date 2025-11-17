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

        List<UUID> filteredProductIds = null;
        List<ProductCategory> categories = null;

        boolean hasCategoryFilter = criteria.category() != null;
        boolean hasSubCategoryFilter = criteria.subCategory() != null;

        if (hasCategoryFilter || hasSubCategoryFilter) {

            String categoryName = hasCategoryFilter ? criteria.category().name() : null;
            String subCategoryName = hasSubCategoryFilter ? criteria.subCategory().name() : null;

            if (hasCategoryFilter && hasSubCategoryFilter) {
                categories = nosqlRepository.findByCategoryAndSubcategory(categoryName, subCategoryName);
            } else if (hasSubCategoryFilter) {
                categories = nosqlRepository.findBySubcategory(subCategoryName);
            } else {
                categories = nosqlRepository.findByCategory(categoryName);
            }

            if (categories != null) {
                filteredProductIds = categories.stream().map(ProductCategory::getId).toList();
            }
        }

        Specification<ProductJpaEntity> spec = buildSqlSpecification(criteria, filteredProductIds);
        Pageable pageable = PageRequest.of(criteria.page(), criteria.size(), Sort.by(criteria.sortBy()));

        Page<ProductJpaEntity> productPage = sqlRepository.findAll(spec, pageable);
        Map<UUID, ProductCategory> categoryDataMap;

        if (categories != null) {
            categoryDataMap = categories.stream()
                    .collect(Collectors.toMap(ProductCategory::getId, Function.identity()));
        } else {
            List<UUID> productIds = productPage.getContent().stream()
                    .map(ProductJpaEntity::getId)
                    .toList();

            List<ProductCategory> productCategories = nosqlRepository.findByIds(productIds);

            categoryDataMap = productCategories.stream()
                    .collect(Collectors.toMap(ProductCategory::getId, Function.identity()));
        }

        List<ProductDetailReadModel> readModels = productPage.getContent().stream()
                .map(jpaEntity -> mapToReadModelWithNoSql(jpaEntity, categoryDataMap))
                .toList();

        Objects.requireNonNull(readModels);

        return new PageImpl<>(readModels, pageable, productPage.getTotalElements());
    }

    private ProductDetailReadModel mapToReadModelWithNoSql(
            ProductJpaEntity jpaEntity,
            Map<UUID, ProductCategory> categoryDataMap) {

        ProductCategory nosqlCategory = null;
        if (categoryDataMap != null)
            nosqlCategory = categoryDataMap.get(jpaEntity.getId());

        return productDetailMapper.toReadModel(jpaEntity, nosqlCategory);
    }

    private Specification<ProductJpaEntity> buildSqlSpecification(
            ProductSearchCriteria criteria,
            List<UUID> includedIds) {

        List<Specification<ProductJpaEntity>> specifications = new ArrayList<>();

        if (criteria.name() != null && !criteria.name().isBlank())
            specifications.add((root, query, cb) -> cb.like(cb.lower(root.get("name")),
                    "%" + criteria.name().toLowerCase() + "%"));

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
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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class ProductDetailReadAdapter implements ProductDetailReadPort {

    private final ProductJpaRepository sqlRepository;
    private final NoSqlRepository<ProductCategory> nosqlRepository;
    private final ProductMapper productDetailMapper;

    public ProductDetailReadAdapter(
            ProductJpaRepository sqlRepository,
            NoSqlRepository<ProductCategory> nosqlRepository,
            ProductMapper productDetailMapper) {
        this.sqlRepository = sqlRepository;
        this.nosqlRepository = nosqlRepository;
        this.productDetailMapper = productDetailMapper;
    }

    @Override
    public Page<ProductDetailReadModel> findProductsByCriteria(ProductSearchCriteria criteria) {
        List<ProductCategory> categories = getCategoriesByCriteria(criteria);

        List<UUID> filteredProductIds = extractProductIds(categories);
        Specification<ProductJpaEntity> spec = buildConciseSpecification(criteria, filteredProductIds);

        Pageable pageable = PageRequest.of(criteria.page(), criteria.size(), Sort.by(criteria.sortBy()));
        Page<ProductJpaEntity> productPage = sqlRepository.findAll(spec, pageable);

        Map<UUID, ProductCategory> categoryDataMap = categories.stream()
                .collect(Collectors.toMap(ProductCategory::getId, Function.identity()));

        List<ProductDetailReadModel> readModels = productPage.getContent().stream()
                .map(jpaEntity -> productDetailMapper.toReadModel(jpaEntity, categoryDataMap.get(jpaEntity.getId())))
                .toList();

        Objects.requireNonNull(readModels);
        return new PageImpl<>(readModels, pageable, productPage.getTotalElements());
    }

    private List<ProductCategory> getCategoriesByCriteria(ProductSearchCriteria criteria) {
        if (criteria.category() == null && criteria.subCategory() == null)
            return List.of();

        boolean hasCategory = criteria.category() != null;
        boolean hasSubCategory = criteria.subCategory() != null;

        if (hasCategory && hasSubCategory) {
            return nosqlRepository.findByCategoryAndSubcategory(
                    criteria.category().name(), criteria.subCategory().name());
        } else if (hasSubCategory) {
            return nosqlRepository.findBySubcategory(criteria.subCategory().name());
        } else if (hasCategory) {
            return nosqlRepository.findByCategory(criteria.category().name());
        }
        return List.of();
    }

    private List<UUID> extractProductIds(List<ProductCategory> categories) {
        if (categories == null || categories.isEmpty()) {
            return List.of();
        }
        return categories.stream()
                .map(ProductCategory::getId)
                .filter(Objects::nonNull)
                .toList();
    }

    private Specification<ProductJpaEntity> buildConciseSpecification(
            ProductSearchCriteria criteria,
            List<UUID> includedIds) {

        List<Specification<ProductJpaEntity>> specifications = new ArrayList<>();

        Optional.ofNullable(criteria.name())
                .filter(name -> !name.isBlank())
                .ifPresent(name -> {
                    String searchName = "%" + name.toLowerCase() + "%";
                    specifications.add((root, query, cb) -> cb.like(cb.lower(root.get("name")), searchName));
                });

        Optional.ofNullable(criteria.priceMin())
                .ifPresent(min -> specifications
                        .add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("priceAmount"), min)));

        Optional.ofNullable(criteria.priceMax())
                .ifPresent(max -> specifications
                        .add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("priceAmount"), max)));

        if (includedIds != null && !includedIds.isEmpty())
            specifications.add((root, query, cb) -> root.get("id").in(includedIds));

        return Specification.allOf(specifications);
    }
}
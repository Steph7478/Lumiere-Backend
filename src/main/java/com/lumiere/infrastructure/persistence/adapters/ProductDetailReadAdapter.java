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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductDetailReadAdapter implements ProductDetailReadPort {

    private final ProductJpaRepository sqlRepository;
    private final NoSqlRepository<ProductCategory> nosqlRepository;
    private final ProductMapper productDetailMapper;

    public ProductDetailReadAdapter(ProductJpaRepository sqlRepository,
            NoSqlRepository<ProductCategory> nosqlRepository, ProductMapper productDetailMapper) {
        this.sqlRepository = sqlRepository;
        this.nosqlRepository = nosqlRepository;
        this.productDetailMapper = productDetailMapper;
    }

    @Override
    public Page<ProductDetailReadModel> findProductsByCriteria(ProductSearchCriteria criteria) {
        List<ProductCategory> categories = getCategories(criteria);
        List<UUID> productIds = categories.stream().map(ProductCategory::getId).filter(Objects::nonNull).toList();

        Specification<ProductJpaEntity> spec = buildSpec(criteria, productIds);

        Pageable pageable = PageRequest.of(criteria.page(), criteria.size(), Sort.by(criteria.sortBy()));
        Page<ProductJpaEntity> productPage = sqlRepository.findAll(spec, pageable);

        Map<UUID, ProductCategory> categoryMap = categories.stream()
                .collect(Collectors.toMap(ProductCategory::getId, Function.identity()));

        List<ProductDetailReadModel> readModels = productPage.getContent().stream()
                .map(jpaEntity -> productDetailMapper.toReadModel(jpaEntity, categoryMap.get(jpaEntity.getId())))
                .toList();

        Objects.requireNonNull(readModels);
        return new PageImpl<>(readModels, pageable, productPage.getTotalElements());
    }

    private List<ProductCategory> getCategories(ProductSearchCriteria c) {
        if (c.category() == null && c.subCategory() == null)
            return List.of();

        String cat = (c.category() != null) ? c.category().name() : null;
        String sub = (c.subCategory() != null) ? c.subCategory().name() : null;

        if (cat != null && sub != null)
            return nosqlRepository.findByCategoryAndSubcategory(cat, sub);
        if (sub != null)
            return nosqlRepository.findBySubcategory(sub);
        if (cat != null)
            return nosqlRepository.findByCategory(cat);
        return List.of();
    }

    private Specification<ProductJpaEntity> buildSpec(ProductSearchCriteria c, List<UUID> ids) {
        List<Specification<ProductJpaEntity>> specs = new ArrayList<>();

        Optional.ofNullable(c.name()).filter(n -> !n.isBlank()).ifPresent(n -> specs.add(
                (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + n.toLowerCase() + "%")));

        Optional.ofNullable(c.priceMin()).ifPresent(min -> specs.add(
                (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("priceAmount"), min)));

        Optional.ofNullable(c.priceMax()).ifPresent(max -> specs.add(
                (root, query, cb) -> cb.lessThanOrEqualTo(root.get("priceAmount"), max)));

        if (!ids.isEmpty())
            specs.add((root, query, cb) -> root.get("id").in(ids));

        return Specification.allOf(specs);
    }
}
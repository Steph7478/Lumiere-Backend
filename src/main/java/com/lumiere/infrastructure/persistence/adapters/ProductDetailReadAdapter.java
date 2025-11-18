package com.lumiere.infrastructure.persistence.adapters;

import com.lumiere.application.dtos.product.query.ProductSearchCriteria;
import com.lumiere.application.ports.ProductDetailReadPort;
import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.repositories.NoSqlRepository;
import com.lumiere.infrastructure.mappers.ProductMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepositoryAdapter;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductDetailReadAdapter implements ProductDetailReadPort {

    private final ProductJpaRepository sqlRepository;
    private final ProductJpaRepositoryAdapter sqlRepositoryAdapter;
    private final NoSqlRepository<ProductCategory> nosqlRepository;
    private final ProductMapper productDetailMapper;

    public ProductDetailReadAdapter(
            ProductJpaRepository sqlRepository,
            ProductJpaRepositoryAdapter sqlRepositoryAdapter,
            NoSqlRepository<ProductCategory> nosqlRepository,
            ProductMapper productDetailMapper) {
        this.sqlRepository = sqlRepository;
        this.sqlRepositoryAdapter = sqlRepositoryAdapter;
        this.nosqlRepository = nosqlRepository;
        this.productDetailMapper = productDetailMapper;
    }

    @Override
    public Optional<ProductDetailReadModel> findProductDetailById(UUID id) {
        Optional<Product> productDomainOptional = sqlRepositoryAdapter.findById(id);

        if (productDomainOptional.isEmpty()) {
            return Optional.empty();
        }

        ProductJpaEntity productJpa = productDetailMapper.toJpa(productDomainOptional.get());

        ProductCategory category = nosqlRepository.findById(productJpa.getId());

        ProductDetailReadModel readModel = productDetailMapper.toReadModel(productJpa, category);

        return Optional.of(readModel);
    }

    @SuppressWarnings("null")
    @Override
    public Page<ProductDetailReadModel> findProductsByCriteria(ProductSearchCriteria criteria) {

        List<ProductCategory> categories = getCategoriesFromNoSql(criteria);
        List<UUID> productIds = categories.stream()
                .map(ProductCategory::getId)
                .filter(Objects::nonNull)
                .toList();

        boolean categoryFilterApplied = criteria.category() != null || criteria.subCategory() != null;
        if (categoryFilterApplied && productIds.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(),
                    PageRequest.of(criteria.page(), criteria.size(), Sort.by(criteria.sortBy())), 0);
        }

        Pageable pageable = PageRequest.of(criteria.page(), criteria.size(), Sort.by(criteria.sortBy()));
        Page<ProductJpaEntity> productPage = sqlRepository.findFilteredProducts(
                productIds,
                criteria.name(),
                criteria.priceMin(),
                criteria.priceMax(),
                pageable);

        Map<UUID, ProductCategory> categoryMap = categories.stream()
                .collect(Collectors.toMap(ProductCategory::getId, Function.identity(), (a, b) -> a));

        List<ProductDetailReadModel> readModels = productPage.getContent().stream()
                .map(jpaEntity -> productDetailMapper.toReadModel(jpaEntity, categoryMap.get(jpaEntity.getId())))
                .toList();

        return new PageImpl<>(readModels, pageable, productPage.getTotalElements());
    }

    private List<ProductCategory> getCategoriesFromNoSql(ProductSearchCriteria c) {
        if (c.category() == null && c.subCategory() == null)
            return Collections.emptyList();

        final String cat = c.category() != null ? c.category().name() : null;
        final String sub = c.subCategory() != null ? c.subCategory().name() : null;

        if (cat != null && sub != null) {
            return nosqlRepository.findByCategoryAndSubcategory(cat, sub);
        } else if (sub != null) {
            return nosqlRepository.findBySubcategory(sub);
        } else if (cat != null) {
            return nosqlRepository.findByCategory(cat);
        }
        return Collections.emptyList();
    }
}
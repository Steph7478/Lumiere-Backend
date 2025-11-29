package com.lumiere.infrastructure.persistence.adapters;

import com.lumiere.application.dtos.product.query.ProductFindAllCriteria;
import com.lumiere.application.dtos.product.query.ProductSearchCriteria;
import com.lumiere.application.ports.ProductDetailReadPort;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.infrastructure.mappers.ProductMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.product.ProductJpaRepository;
import com.lumiere.infrastructure.persistence.nosql.entities.ProductCategoryEntity;
import com.lumiere.infrastructure.persistence.nosql.repository.ProductCategoryRepository; // CORRIGIDO PARA A INTERFACE QUE RETORNA *ENTITY
import com.lumiere.infrastructure.persistence.utils.CriteriaUtil;
import com.lumiere.infrastructure.persistence.utils.PaginationConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional(readOnly = true)
public class ProductDetailReadAdapter implements ProductDetailReadPort {

    private final ProductJpaRepository sqlRepository;
    private final ProductCategoryRepository nosqlReadRepository;
    private final ProductMapper productDetailMapper;

    public ProductDetailReadAdapter(
            ProductJpaRepository sqlRepository,
            ProductCategoryRepository nosqlReadRepository,
            ProductMapper productDetailMapper) {
        this.sqlRepository = sqlRepository;
        this.nosqlReadRepository = nosqlReadRepository;
        this.productDetailMapper = productDetailMapper;
    }

    @Override
    public Optional<ProductDetailReadModel> findProductDetailById(UUID id) {
        Optional<ProductJpaEntity> productJpaOptional = sqlRepository.findById(id);

        if (productJpaOptional.isEmpty())
            return Optional.empty();

        ProductJpaEntity productJpa = productJpaOptional.get();

        Optional<ProductCategoryEntity> categoryOptional = nosqlReadRepository
                .findById(Objects.requireNonNull(productJpa.getId()));

        ProductDetailReadModel readModel = productDetailMapper.toReadModel(productJpa, categoryOptional.orElse(null));

        return Optional.of(readModel);
    }

    @Override
    public Page<ProductDetailReadModel> findAllProducts(ProductFindAllCriteria criteria) {
        Pageable pageable = CriteriaUtil.buildPageable(criteria.page(), criteria.size(), criteria.sortBy());

        Page<ProductJpaEntity> productPage = sqlRepository.findAll(Objects.requireNonNull(pageable));

        if (productPage.isEmpty())
            return PaginationConverter.emptyPage(pageable);

        List<UUID> pageProductIds = productPage.getContent().stream()
                .map(ProductJpaEntity::getId)
                .toList();

        List<ProductCategoryEntity> categoriesOnPage = getCategoriesByIds(pageProductIds);

        Map<UUID, ProductCategoryEntity> categoryMap = categoriesOnPage.stream()
                .collect(Collectors.toMap(ProductCategoryEntity::getId, Function.identity(), (a, b) -> a));

        return PaginationConverter.convert(productPage,
                jpaEntity -> productDetailMapper.toReadModel(jpaEntity, categoryMap.get(jpaEntity.getId())));
    }

    @Override
    public Page<ProductDetailReadModel> findProductsByCriteria(ProductSearchCriteria criteria) {

        List<UUID> filteredProductIds = Collections.emptyList();
        boolean categoryFilterApplied = criteria.category() != null || criteria.subCategory() != null;

        if (categoryFilterApplied) {
            List<ProductCategoryEntity> filteredCategories = getCategoriesFromNoSql(criteria);
            filteredProductIds = filteredCategories.stream()
                    .map(ProductCategoryEntity::getId)
                    .filter(Objects::nonNull)
                    .toList();
        }

        Pageable pageable = CriteriaUtil.buildPageable(criteria.page(), criteria.size(), criteria.sortBy());

        if (categoryFilterApplied && filteredProductIds.isEmpty())
            return PaginationConverter.emptyPage(pageable);

        Page<ProductJpaEntity> productPage = sqlRepository.findFilteredProducts(
                filteredProductIds,
                criteria.name(),
                criteria.priceMin(),
                criteria.priceMax(),
                pageable);

        List<UUID> pageProductIds = productPage.getContent().stream()
                .map(ProductJpaEntity::getId)
                .toList();

        List<ProductCategoryEntity> categoriesOnPage = getCategoriesByIds(pageProductIds);

        Map<UUID, ProductCategoryEntity> categoryMap = categoriesOnPage.stream()
                .collect(Collectors.toMap(ProductCategoryEntity::getId, Function.identity(), (a, b) -> a));

        return PaginationConverter.convert(productPage,
                jpaEntity -> productDetailMapper.toReadModel(jpaEntity, categoryMap.get(jpaEntity.getId())));
    }

    private List<ProductCategoryEntity> getCategoriesFromNoSql(ProductSearchCriteria c) {

        final String cat = c.category() != null ? c.category().name() : null;
        final String sub = c.subCategory() != null ? c.subCategory().name() : null;

        if (cat != null && sub != null) {
            return nosqlReadRepository.findByCategoryNameAndSubcategoryName(cat, sub);
        } else if (sub != null) {
            return nosqlReadRepository.findBySubcategoryName(sub);
        } else if (cat != null) {
            return nosqlReadRepository.findByCategoryName(cat);
        }
        return Collections.emptyList();
    }

    private List<ProductCategoryEntity> getCategoriesByIds(List<UUID> ids) {
        Iterable<ProductCategoryEntity> iterable = nosqlReadRepository.findAllById(Objects.requireNonNull(ids));
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
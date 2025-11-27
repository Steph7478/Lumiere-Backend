package com.lumiere.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.factories.ProductFactory;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

@Mapper(componentModel = "spring", uses = { RatingMapper.class }, imports = {
                Money.class,
                Stock.class,
                CurrencyType.class,
                ProductFactory.class
}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends BaseMapper<Product, ProductJpaEntity> {

        @Mapping(target = "priceAmount", expression = "java(domain.getPrice().getAmount())")
        @Mapping(target = "priceCurrency", expression = "java(domain.getPrice().getCurrency().name())")
        @Mapping(target = "stockQuantity", expression = "java(domain.getStock().getQuantity())")
        @Mapping(target = "ratings", source = "ratings")
        ProductJpaEntity map(Product domain);

        @Mapping(target = "priceAmount", expression = "java(domain.getPrice().getAmount())")
        @Mapping(target = "priceCurrency", expression = "java(domain.getPrice().getCurrency().name())")
        @Mapping(target = "stockQuantity", expression = "java(domain.getStock().getQuantity())")
        ProductJpaEntity toJpa(Product domain, Object... context);

        @Mapping(target = "category", expression = "java(nosqlCategory != null ? nosqlCategory.getCategory() : null)")
        @Mapping(target = "subCategory", expression = "java(nosqlCategory != null ? nosqlCategory.getSubcategory() : null)")
        @Mapping(target = "stock", source = "jpaEntity.stockQuantity")
        @Mapping(target = "price", expression = "java(new Money(jpaEntity.getPriceAmount(), CurrencyType.valueOf(jpaEntity.getPriceCurrency())))")
        @Mapping(target = "id", source = "jpaEntity.id")
        @Mapping(target = "createdAt", source = "jpaEntity.createdAt")
        @Mapping(target = "updatedAt", source = "jpaEntity.updatedAt")
        ProductDetailReadModel toReadModel(ProductJpaEntity jpaEntity, ProductCategory nosqlCategory);

        @Mapping(target = "createdAt", expression = "java(jpaEntity.getCreatedAt())")
        @Mapping(target = "updatedAt", expression = "java(jpaEntity.getUpdatedAt())")
        default Product toDomain(ProductJpaEntity jpaEntity) {

                return ProductFactory.from(
                                jpaEntity.getId(),
                                jpaEntity.getName(),
                                jpaEntity.getDescription(),
                                new Money(
                                                jpaEntity.getPriceAmount(),
                                                CurrencyType.valueOf(jpaEntity.getPriceCurrency())),
                                null,
                                new Stock(jpaEntity.getStockQuantity()));
        }
}
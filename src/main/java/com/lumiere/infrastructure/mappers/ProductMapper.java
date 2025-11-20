package com.lumiere.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;
import com.lumiere.domain.entities.Rating;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { RatingMapper.class }, imports = {
                Money.class,
                Stock.class,
                CurrencyType.class,
                Collections.class,
                UUID.class,
                Category.class,
                SubCategory.class,
                BigDecimal.class,
                List.class,
                Collectors.class
}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends BaseMapper<Product, ProductJpaEntity> {

        ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

        @Mapping(target = "priceAmount", source = "price.amount")
        @Mapping(target = "priceCurrency", source = "price.currency", qualifiedByName = "currencyTypeToString")
        @Mapping(target = "stockQuantity", source = "stock.quantity")
        @Mapping(target = "ratings", source = "ratings")
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        ProductJpaEntity toJpa(Product domain);

        @Mapping(target = "ratings", ignore = true)
        Product toDomain(ProductJpaEntity jpaEntity);

        @Mapping(target = "stock", source = "jpaEntity.stockQuantity")
        @Mapping(target = "ratings", source = "jpaEntity.ratings")
        @Mapping(target = "id", expression = "java(jpaEntity.getId() != null ? jpaEntity.getId().toString() : null)")
        @Mapping(target = "price", expression = "java(new Money(jpaEntity.getPriceAmount(), CurrencyType.valueOf(jpaEntity.getPriceCurrency())))")
        @Mapping(target = "category", expression = "java(nosqlCategory != null ? nosqlCategory.getCategory() : null)")
        @Mapping(target = "subCategory", expression = "java(nosqlCategory != null ? nosqlCategory.getSubcategory() : null)")
        @Mapping(target = "createdAt", source = "jpaEntity.createdAt")
        @Mapping(target = "updatedAt", source = "jpaEntity.updatedAt")
        ProductDetailReadModel toReadModel(ProductJpaEntity jpaEntity, ProductCategory nosqlCategory);

        @ObjectFactory
        default Product createProduct(ProductJpaEntity jpaEntity, @TargetType Class<Product> targetType) {

                final RatingMapper ratingMapper = Mappers.getMapper(RatingMapper.class);

                Money price = new Money(jpaEntity.getPriceAmount(), CurrencyType.valueOf(jpaEntity.getPriceCurrency()));
                Stock stock = new Stock(jpaEntity.getStockQuantity());

                List<Rating> ratings = jpaEntity.getRatings() != null ? jpaEntity.getRatings().stream()
                                .map(ratingMapper::toDomain)
                                .collect(Collectors.toList()) : Collections.emptyList();

                return Product.from(
                                jpaEntity.getId(),
                                jpaEntity.getName(),
                                jpaEntity.getDescription(),
                                price,
                                ratings,
                                stock);
        }

        @Named("currencyTypeToString")
        default String currencyTypeToString(CurrencyType currencyType) {
                return currencyType != null ? currencyType.name() : null;
        }
}
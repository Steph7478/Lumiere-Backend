package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Product;
import com.lumiere.domain.entities.ProductCategory;
import com.lumiere.domain.entities.Rating;
import com.lumiere.domain.enums.CategoriesEnum.Category;
import com.lumiere.domain.enums.CategoriesEnum.SubCategory;
import com.lumiere.domain.enums.CurrencyEnum.CurrencyType;
import com.lumiere.domain.readmodels.ProductDetailReadModel;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.ProductJpaEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", uses = { RatingMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends BaseMapper<Product, ProductJpaEntity> {

        @Mappings({
                        @Mapping(target = "id", source = "domain.id"),
                        @Mapping(target = "createdAt", ignore = true),
                        @Mapping(target = "updatedAt", ignore = true),
                        @Mapping(target = "priceAmount", source = "domain.price.amount"),
                        @Mapping(target = "priceCurrency", source = "domain.price.currency", qualifiedByName = "mapCurrencyTypeToString"),
                        @Mapping(target = "stockQuantity", source = "domain.stock.quantity"),
                        @Mapping(target = "ratings", ignore = true)
        })
        ProductJpaEntity toJpa(Product domain);

        @Named("mapCurrencyTypeToString")
        default String mapCurrencyTypeToString(CurrencyType currencyType) {
                if (currencyType == null)
                        return null;

                return currencyType.name();
        }

        @Mappings({
                        @Mapping(target = ".", source = "jpaEntity", qualifiedByName = "instantiateProductFromJpa"),
                        @Mapping(target = "ratings", ignore = true)
        })
        Product toDomain(ProductJpaEntity jpaEntity);

        @Named("instantiateProductFromJpa")
        default Product instantiateProductFromJpa(ProductJpaEntity jpaEntity, List<Rating> ratings) {
                Money price = createMoney(jpaEntity);
                Stock stock = createStock(jpaEntity.getStockQuantity());

                return Product.from(
                                jpaEntity.getId(),
                                jpaEntity.getName(),
                                jpaEntity.getDescription(),
                                price,
                                ratings,
                                stock);
        }

        default Money createMoney(ProductJpaEntity jpaEntity) {
                if (jpaEntity.getPriceCurrency() == null)
                        return null;

                return new Money(
                                jpaEntity.getPriceAmount(),
                                CurrencyType.valueOf(jpaEntity.getPriceCurrency()));
        }

        default Stock createStock(Integer quantity) {
                return new Stock(quantity);
        }

        @Mappings({
                        @Mapping(target = ".", source = "jpaEntity", qualifiedByName = "createProductDetailReadModel"),
                        @Mapping(target = "ratings", source = "jpaEntity.ratings"),
                        @Mapping(target = "id", ignore = true),
                        @Mapping(target = "createdAt", ignore = true),
                        @Mapping(target = "updatedAt", ignore = true)
        })
        ProductDetailReadModel toReadModel(
                        ProductJpaEntity jpaEntity,
                        ProductCategory nosqlCategory);

        @Named("createProductDetailReadModel")
        default ProductDetailReadModel createProductDetailReadModel(
                        ProductJpaEntity jpaEntity,
                        List<Rating> ratings,
                        ProductCategory nosqlCategory) {

                Money price = createMoney(jpaEntity);

                final Category category = nosqlCategory != null ? nosqlCategory.getCategory() : null;
                final SubCategory subCategory = nosqlCategory != null ? nosqlCategory.getSubcategory() : null;

                return new ProductDetailReadModel(
                                jpaEntity.getId() != null ? jpaEntity.getId().toString() : null,
                                jpaEntity.getName(),
                                jpaEntity.getDescription(),
                                price,
                                ratings,
                                jpaEntity.getStockQuantity(),
                                jpaEntity.getCreatedAt(),
                                jpaEntity.getUpdatedAt(),
                                category,
                                subCategory);
        }
}
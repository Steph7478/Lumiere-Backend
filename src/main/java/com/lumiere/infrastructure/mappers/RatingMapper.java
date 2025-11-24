package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Rating;
import com.lumiere.domain.factories.RatingFactory;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.RatingJpaEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper extends BaseMapper<Rating, RatingJpaEntity> {

    @Mapping(target = "productId", source = "productId")
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "comment", source = "comment")
    RatingJpaEntity toJpa(Rating domain);

    @Override
    default RatingJpaEntity toJpa(Rating domain, Object... context) {
        return toJpa(domain);
    }

    Rating toDomain(RatingJpaEntity jpaEntity);

    @ObjectFactory
    default Rating createRating(RatingJpaEntity jpaEntity) {
        return RatingFactory.from(
                jpaEntity.getId(),
                jpaEntity.getProductId(),
                jpaEntity.getRating(),
                jpaEntity.getComment(),
                jpaEntity.getOrderId());
    }
}

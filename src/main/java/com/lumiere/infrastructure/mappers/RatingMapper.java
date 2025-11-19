package com.lumiere.infrastructure.mappers;

import com.lumiere.domain.entities.Rating;
import com.lumiere.infrastructure.mappers.base.BaseMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.RatingJpaEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper extends BaseMapper<Rating, RatingJpaEntity> {

    @Mapping(target = "id", source = "domain.id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RatingJpaEntity toJpa(Rating domain);

    Rating toDomain(RatingJpaEntity jpaEntity);

    @ObjectFactory
    default Rating createRating(RatingJpaEntity jpaEntity) {
        return Rating.rebuild(
                jpaEntity.getId(),
                jpaEntity.getProductId(),
                jpaEntity.getRating(),
                jpaEntity.getComment(),
                jpaEntity.getOrderId());
    }
}
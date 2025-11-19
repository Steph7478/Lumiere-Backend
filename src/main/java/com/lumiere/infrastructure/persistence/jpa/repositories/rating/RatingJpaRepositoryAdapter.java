package com.lumiere.infrastructure.persistence.jpa.repositories.rating;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.Rating;
import com.lumiere.domain.repositories.RatingRepository;
import com.lumiere.infrastructure.mappers.RatingMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.RatingJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

@Repository
public class RatingJpaRepositoryAdapter extends BaseRepositoryAdapter<Rating, RatingJpaEntity>
        implements RatingRepository {

    protected RatingJpaRepositoryAdapter(RatingJpaRepository jpaRepository,
            RatingMapper mapper, EntityManager entityManager) {
        super(jpaRepository, mapper, entityManager, RatingJpaEntity.class);
    }

}

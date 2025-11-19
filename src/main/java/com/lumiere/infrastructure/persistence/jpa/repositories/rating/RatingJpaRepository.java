package com.lumiere.infrastructure.persistence.jpa.repositories.rating;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.persistence.jpa.entities.RatingJpaEntity;

@Repository
public interface RatingJpaRepository extends JpaRepository<RatingJpaEntity, UUID> {

}

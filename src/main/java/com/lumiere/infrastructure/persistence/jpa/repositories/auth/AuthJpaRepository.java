package com.lumiere.infrastructure.persistence.repositories.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.persistence.entities.AuthJpaEntity;

@Repository
public interface AuthJpaRepository extends JpaRepository<AuthJpaEntity, UUID> {

    boolean existsByEmail(String email);

    Optional<AuthJpaEntity> findByEmail(String email);
}

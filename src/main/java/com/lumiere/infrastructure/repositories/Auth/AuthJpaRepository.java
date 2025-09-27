package com.lumiere.infrastructure.repositories.Auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.jpa.AuthJpaEntity;

@Repository
public interface AuthJpaRepository extends JpaRepository<AuthJpaEntity, UUID> {

    boolean existsByEmail(String email);

    Optional<AuthJpaEntity> findByEmail(String email);
}

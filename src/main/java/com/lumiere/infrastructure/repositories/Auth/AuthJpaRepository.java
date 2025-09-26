package com.lumiere.infrastructure.repositories.Auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.jpa.AuthJpaEntity;

@Repository
public interface AuthJpaRepository extends JpaRepository<AuthJpaEntity, UUID> {

    @Query("SELECT a FROM AuthJpaEntity a WHERE a.email = :email")
    Optional<AuthJpaEntity> findByEmailCustom(@Param("email") String email);

    @Query("SELECT a FROM AuthJpaEntity a WHERE a.id = :id")
    Optional<AuthJpaEntity> findAuthById(@Param("id") UUID id);

}

package com.lumiere.infrastructure.repositories.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lumiere.infrastructure.jpa.UserJpaEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {

    @Query("SELECT user FROM UserJpaEntity user WHERE user.id = :id")
    Optional<UserJpaEntity> findUserById(@Param("id") UUID id);

    @Query("SELECT user FROM UserJpaEntity user WHERE user.auth.email = :email")
    Optional<UserJpaEntity> findUserByEmail(@Param("email") String email);

}

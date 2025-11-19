package com.lumiere.infrastructure.persistence.jpa.repositories.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.domain.readmodels.AuthInfoView;
import com.lumiere.domain.readmodels.UserInfoView;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
    @EntityGraph(attributePaths = { "auth" })
    Optional<UserJpaEntity> findByAuthEmail(String email);

    @EntityGraph(attributePaths = { "auth" })
    Optional<AuthInfoView> findAuthInfoByAuthId(UUID id);

    @EntityGraph(attributePaths = { "auth" })
    Optional<UserInfoView> findUserInfoById(UUID id);

    @EntityGraph(attributePaths = { "auth" })
    Optional<UserJpaEntity> findUserByAuthId(UUID id);
}

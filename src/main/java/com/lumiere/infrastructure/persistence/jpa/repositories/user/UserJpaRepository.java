package com.lumiere.infrastructure.persistence.jpa.repositories.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lumiere.domain.readmodels.AuthInfoView;
import com.lumiere.domain.readmodels.UserInfoView;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {
    Optional<UserJpaEntity> findByAuthEmail(String email);

    Optional<AuthInfoView> findAuthInfoByAuthId(UUID id);

    Optional<UserInfoView> findUserInfoById(UUID id);

    Optional<UserJpaEntity> findUserByAuthId(UUID id);
}

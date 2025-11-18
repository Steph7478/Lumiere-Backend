package com.lumiere.infrastructure.persistence.jpa.repositories.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.AuthInfoView;
import com.lumiere.domain.readmodels.UserInfoView;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.mappers.UserMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.UserJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.auth.AuthJpaRepository;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;

import jakarta.persistence.EntityManager;

@Repository
public class UserJpaRepositoryAdapter extends BaseRepositoryAdapter<User, UserJpaEntity>
        implements UserRepository {

    public UserJpaRepositoryAdapter(
            UserJpaRepository userRepo,
            AuthJpaRepository authRepo,
            UserMapper userMapper,
            EntityManager entityManager) {
        super(userRepo, userMapper, entityManager, UserJpaEntity.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByAuthEmail(String email) {
        return ((UserJpaRepository) jpaRepository)
                .findByAuthEmail(email)
                .map(((UserMapper) mapper)::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByAuthId(UUID id) {
        return ((UserJpaRepository) jpaRepository).findUserByAuthId(id).map(((UserMapper) mapper)::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserInfoView> findUserInfoById(UUID id) {
        return ((UserJpaRepository) jpaRepository).findUserInfoById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthInfoView> findAuthInfoByAuthId(UUID id) {
        return ((UserJpaRepository) jpaRepository).findAuthInfoByAuthId(id);
    }

}

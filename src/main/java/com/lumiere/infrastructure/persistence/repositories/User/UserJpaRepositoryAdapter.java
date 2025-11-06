package com.lumiere.infrastructure.persistence.repositories.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.User;
import com.lumiere.domain.readmodels.AuthInfoView;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.mappers.UserMapper;
import com.lumiere.infrastructure.persistence.entities.UserJpaEntity;
import com.lumiere.infrastructure.persistence.repositories.Auth.AuthJpaRepository;
import com.lumiere.infrastructure.persistence.repositories.base.BaseRepositoryAdapter;
import com.lumiere.shared.annotations.validators.ValidEntityGraphPaths;

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
    public Optional<User> findByIdWithRelations(
            UUID id,
            @ValidEntityGraphPaths(root = UserJpaEntity.class, allowedPaths = { "auth" }) String... relations) {
        return findByIdWithEager(id, relations);
    }

    @Override
    public List<User> findAllWithRelations() {
        return findAllWithEager("auth");
    }

    @Override
    public Optional<User> findByAuthEmail(String email) {
        return ((UserJpaRepository) jpaRepository)
                .findByAuthEmail(email)
                .map(((UserMapper) mapper)::toDomain);
    }

    @Override
    public Optional<AuthInfoView> findAuthInfoByAuthId(UUID id) {
        return ((UserJpaRepository) jpaRepository).findAuthInfoByAuthId(id);
    }
}

package com.lumiere.infrastructure.persistence.repositories.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.UserRepository;
import com.lumiere.infrastructure.mappers.AuthMapper;
import com.lumiere.infrastructure.mappers.UserMapper;
import com.lumiere.infrastructure.persistence.entities.AuthJpaEntity;
import com.lumiere.infrastructure.persistence.entities.UserJpaEntity;
import com.lumiere.infrastructure.persistence.repositories.Auth.AuthJpaRepository;
import com.lumiere.infrastructure.persistence.repositories.base.BaseRepositoryAdapter;
import com.lumiere.shared.annotations.validators.ValidEntityGraphPaths;

import jakarta.persistence.EntityManager;

@Repository
public class UserJpaRepositoryAdapter extends BaseRepositoryAdapter<User, UserJpaEntity>
        implements UserRepository {

    private final AuthJpaRepository authRepo;
    private final AuthMapper authMapper;

    public UserJpaRepositoryAdapter(
            UserJpaRepository userRepo,
            AuthJpaRepository authRepo,
            AuthMapper authMapper,
            UserMapper userMapper,
            EntityManager entityManager) {
        super(userRepo, userMapper, entityManager, UserJpaEntity.class);
        this.authRepo = authRepo;
        this.authMapper = authMapper;
    }

    @Override
    public User save(User user) {
        AuthJpaEntity authEntity = authMapper.toJpa(user.getAuth());
        Objects.requireNonNull(authEntity, "auth entity cannot be null");
        AuthJpaEntity savedAuth = authRepo.save(authEntity);
        user.setAuth(authMapper.toDomain(savedAuth));

        return super.save(user);
    }

    @Override
    public Optional<User> findByAuthEmail(String email) {
        return ((UserJpaRepository) jpaRepository)
                .findByAuthEmail(email)
                .map(((UserMapper) mapper)::toDomain);
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

}

package com.lumiere.infrastructure.persistence.jpa.repositories.auth;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.infrastructure.mappers.AuthMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.AuthJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;
import com.lumiere.shared.annotations.validators.ValidEntityGraphPaths;

import jakarta.persistence.EntityManager;

@Repository
public class AuthJpaRepositoryAdapter extends BaseRepositoryAdapter<Auth, AuthJpaEntity>
        implements AuthRepository {

    private final AuthMapper authMapper;
    private final AuthJpaRepository authRepo;

    public AuthJpaRepositoryAdapter(
            AuthJpaRepository authRepo,
            AuthMapper authMapper,
            EntityManager entityManager) {
        super(authRepo, authMapper, entityManager, AuthJpaEntity.class);
        this.authMapper = authMapper;
        this.authRepo = authRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Auth> findById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        return super.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Auth> findByEmail(String email) {
        Objects.requireNonNull(email, "email cannot be null");
        return authRepo.findByEmail(email)
                .map(authMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Auth> findByIdWithRelations(UUID id,
            @ValidEntityGraphPaths(root = AuthJpaEntity.class, allowedPaths = { "user" }) String... relations) {
        return findByIdWithEager(id, relations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllWithRelations() {
        throw new UnsupportedOperationException("Unimplemented method 'findAllWithRelations'");
    }
}

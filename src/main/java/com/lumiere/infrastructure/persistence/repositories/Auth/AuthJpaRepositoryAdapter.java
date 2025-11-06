package com.lumiere.infrastructure.persistence.repositories.Auth;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.infrastructure.mappers.AuthMapper;
import com.lumiere.infrastructure.persistence.entities.AuthJpaEntity;
import com.lumiere.infrastructure.persistence.repositories.base.BaseRepositoryAdapter;
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
    public Optional<Auth> findById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        return super.findById(id);
    }

    @Override
    public Optional<Auth> findByEmail(String email) {
        Objects.requireNonNull(email, "email cannot be null");
        return authRepo.findByEmail(email)
                .map(authMapper::toDomainFull);
    }

    @Override
    public Auth save(Auth auth) {
        Auth saved = super.save(auth);
        return authMapper.toDomain(authMapper.toJpa(saved));
    }

    @Override
    public Optional<Auth> findByIdWithRelations(UUID id,
            @ValidEntityGraphPaths(root = AuthJpaEntity.class, allowedPaths = { "user" }) String... relations) {
        return findByIdWithEager(id, relations);
    }

    @Override
    public List<User> findAllWithRelations() {
        throw new UnsupportedOperationException("Unimplemented method 'findAllWithRelations'");
    }
}

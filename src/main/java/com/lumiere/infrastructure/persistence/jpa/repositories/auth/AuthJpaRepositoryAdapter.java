package com.lumiere.infrastructure.persistence.jpa.repositories.auth;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.infrastructure.mappers.AuthMapper;
import com.lumiere.infrastructure.persistence.jpa.entities.AuthJpaEntity;
import com.lumiere.infrastructure.persistence.jpa.repositories.base.BaseRepositoryAdapter;
import com.lumiere.shared.annotations.validators.validEntityGraphPath.ValidEntityGraphPaths;

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
    @Cacheable(value = "authJpa", key = "#id")
    @Transactional(readOnly = true)
    public Optional<Auth> findById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        return super.findById(id);
    }

    @Override
    @Cacheable(value = "authJpa", key = "#email")
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

    @Override
    @Caching(evict = {
            @CacheEvict(value = "authJpa", key = "#domain.id"),
            @CacheEvict(value = "authJpa", key = "#domain.email")
    })
    @Transactional
    public Auth save(Auth domain) {
        return super.save(domain);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "authJpa", key = "#domain.id"),
            @CacheEvict(value = "authJpa", key = "#domain.email")
    })
    @Transactional
    public Auth update(Auth domain) {
        return super.update(domain);
    }

    @Override
    @CacheEvict(value = "authJpa", key = "#id")
    @Transactional
    public void deleteById(UUID id) {
        super.deleteById(id);
    }
}
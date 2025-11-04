package com.lumiere.infrastructure.repositories.Auth;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.infrastructure.mappers.AuthMapper;

@Repository
public class AuthJpaRepositoryAdapter implements AuthRepository {

    private final AuthJpaRepository authJpaRepo;
    private final AuthMapper authMapper;

    public AuthJpaRepositoryAdapter(AuthJpaRepository authJpaRepo, AuthMapper authMapper) {
        this.authJpaRepo = authJpaRepo;
        this.authMapper = authMapper;
    }

    @Override
    public Auth save(Auth auth) {
        var entity = authMapper.toJpa(auth);
        Objects.requireNonNull(entity, "entity cannot be null");
        var saved = authJpaRepo.save(entity);

        return authMapper.toDomainMe(saved);
    }

    @Override
    public Optional<Auth> findById(UUID id) {
        Objects.requireNonNull(id, "id cannot be null");
        return authJpaRepo.findById(id)
                .map(authMapper::toDomainMe);
    }

    @Override
    public Optional<Auth> findByEmail(String email) {
        Objects.requireNonNull(email, "email cannot be null");
        return authJpaRepo.findByEmail(email)
                .map(authMapper::toDomain);
    }
}

// OBS:
// this "::" is equal to:
// .map(authEntity -> AuthMapper.toDomainSafe(authEntity))
// .map(AuthMapper::toDomainSafe)

// Example:

// Method Reference:
// @Override
// public Auth findById(UUID id) {
// return authJpaRepo.findById(id)
// .map(AuthMapper::toDomainSafe)
// .orElse(null);
// }

// Normal Lambda:
// @Override
// public Auth findById(UUID id) {
// return authJpaRepo.findById(id)
// .map(authEntity -> AuthMapper.toDomainSafe(authEntity))
// .orElse(null);
// }

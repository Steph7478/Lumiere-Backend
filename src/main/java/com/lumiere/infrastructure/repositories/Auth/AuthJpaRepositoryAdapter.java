package com.lumiere.infrastructure.repositories.Auth;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.infrastructure.mappers.AuthMapper;

@Repository
public class AuthJpaRepositoryAdapter implements AuthRepository {

    private final AuthJpaRepository authJpaRepo;

    public AuthJpaRepositoryAdapter(AuthJpaRepository authJpaRepo) {
        this.authJpaRepo = authJpaRepo;
    }

    @Override
    public Auth save(Auth auth) {
        var entity = AuthMapper.toJpa(auth);
        var saved = authJpaRepo.save(entity);
        return AuthMapper.toDomainSafe(saved);
    }

    @Override
    public Auth findById(UUID id) {
        return authJpaRepo.findById(id)
                .map(AuthMapper::toDomainMe)
                .orElse(null);
    }

    @Override
    public Auth findByEmail(String email) {
        return authJpaRepo.findByEmailCustom(email)
                .map(AuthMapper::toDomainSafe)
                .orElse(null);
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

}

package com.lumiere.infrastructure.repositories.Auth;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.AuthRepository;
import com.lumiere.infrastructure.jpa.AuthJpaEntity;
import com.lumiere.infrastructure.mappers.AuthMapper;
import com.lumiere.infrastructure.repositories.base.BaseRepositoryAdapter;

@Repository
public class AuthJpaRepositoryAdapter extends BaseRepositoryAdapter<Auth, AuthJpaEntity>
        implements AuthRepository {

    private final AuthMapper authMapper;
    private final AuthJpaRepository authRepo;

    public AuthJpaRepositoryAdapter(AuthJpaRepository authRepo, AuthMapper authMapper) {
        super(authRepo, authMapper);
        this.authMapper = authMapper;
        this.authRepo = authRepo;
    }

    @Override
    public Optional<Auth> findByEmail(String email) {
        Objects.requireNonNull(email, "email cannot be null");
        return authRepo.findByEmail(email)
                .map(authMapper::toDomainMe);
    }

    @Override
    public Auth save(Auth auth) {
        var saved = super.save(auth);
        return authMapper.toDomainMe(authMapper.toJpa(saved));
    }
}

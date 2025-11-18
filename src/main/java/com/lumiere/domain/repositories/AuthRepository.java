package com.lumiere.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.base.BaseRepository;

public interface AuthRepository extends BaseRepository<Auth> {
    Optional<Auth> findByEmail(String email);

    Optional<Auth> findByIdWithUserEager(UUID id);
}

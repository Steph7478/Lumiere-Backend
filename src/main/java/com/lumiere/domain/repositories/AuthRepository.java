package com.lumiere.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.Auth;

public interface AuthRepository {
    Auth save(Auth auth);

    Optional<Auth> findById(UUID id);

    Optional<Auth> findByEmail(String email);
}

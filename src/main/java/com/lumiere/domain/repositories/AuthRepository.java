package com.lumiere.domain.repositories;

import java.util.UUID;

import com.lumiere.domain.entities.Auth;

public interface AuthRepository {
    Auth save(Auth auth);

    Auth findById(UUID id);

    Auth findByEmail(String email);
}

package com.lumiere.domain.repositories;

import java.util.Optional;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.repositories.base.BaseRepository;

public interface AuthRepository extends BaseRepository<Auth> {
    Optional<Auth> findByEmail(String email);
}

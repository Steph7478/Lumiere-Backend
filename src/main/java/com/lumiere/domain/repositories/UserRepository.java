package com.lumiere.domain.repositories;

import java.util.Optional;

import com.lumiere.domain.entities.User;
import com.lumiere.domain.repositories.base.BaseRepository;

public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByAuthEmail(String email);
}

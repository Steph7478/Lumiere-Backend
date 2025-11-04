package com.lumiere.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.User;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByAuthEmail(String email);
}

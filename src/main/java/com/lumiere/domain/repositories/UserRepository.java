package com.lumiere.domain.repositories;

import java.util.UUID;

import com.lumiere.domain.entities.User;

public interface UserRepository {
    User save(User user);

    User findById(UUID id);

    User findByAuthEmail(String email);
}

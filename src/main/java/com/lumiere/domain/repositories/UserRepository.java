package com.lumiere.domain.repositories;

import java.util.UUID;

import com.lumiere.domain.entities.User;

public interface UserRepository {
    User save(User user);

    User findUserById(UUID id);

    User findUserByEmail(String email);
}

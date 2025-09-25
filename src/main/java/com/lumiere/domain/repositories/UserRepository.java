package com.lumiere.domain.repositories;

import com.lumiere.domain.entities.User;

public interface UserRepository {
    User save(User user);
}

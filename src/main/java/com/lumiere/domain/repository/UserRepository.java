package com.lumiere.domain.repository;

import com.lumiere.domain.entity.User;

public interface UserRepository {
    User save(User user);
}

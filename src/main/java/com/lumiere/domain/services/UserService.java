package com.lumiere.domain.services;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;

public class UserService {

    private UserService() {
    }

    public static User createUser(Auth auth) {
        return User.from(null, auth);
    }
}

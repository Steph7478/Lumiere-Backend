package com.lumiere.domain.services;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;

public class UserService {

    private UserService() {
    }

    public static User createUser(Auth auth) {
        User user = User.from(null, auth);
        auth.setUser(user);
        return user;
    }
}

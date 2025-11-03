package com.lumiere.presentation.routes;

public final class Routes {

    private Routes() {
    }

    public static final class Auth {
        public static final String BASE = "/auth";
        public static final String REGISTER = "/register";
        public static final String LOGIN = "/login";
    }

    public static final class Admin {
        public static final String BASE = "/admin";
    }

}

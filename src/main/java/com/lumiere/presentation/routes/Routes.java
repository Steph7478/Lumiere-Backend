package com.lumiere.presentation.routes;

import com.lumiere.infrastructure.security.config.ApiConfig;
import com.lumiere.shared.constants.Methods;
import com.lumiere.shared.constants.Roles;

import java.util.List;
import java.util.Set;
import java.util.EnumSet;

public final class Routes {

    private Routes() {
    }

    public record Route(String path, EnumSet<Methods> methods, Set<Roles> roles) {
        public String fullPath() {
            return ApiConfig.API_PREFIX + path;
        }
    }

    public static final String REGISTER = "/auth/register";
    public static final Route REGISTER_ROUTE = new Route(REGISTER, EnumSet.of(Methods.POST), Set.of());

    public static final String LOGIN = "/auth/login";
    public static final Route LOGIN_ROUTE = new Route(LOGIN, EnumSet.of(Methods.POST), Set.of());

    public static final List<Route> PUBLIC_ROUTES = List.of(REGISTER_ROUTE, LOGIN_ROUTE);
    public static final List<Route> PRIVATE_ROUTES = List.of();
}

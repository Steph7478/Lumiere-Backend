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

    public static final class AUTH {
        public static final String REGISTER = "/auth/register";
        public static final Route REGISTER_ROUTE = new Route(REGISTER, EnumSet.of(Methods.POST), Set.of());

        public static final String LOGIN = "/auth/login";
        public static final Route LOGIN_ROUTE = new Route(LOGIN, EnumSet.of(Methods.POST), Set.of());

        public static final List<Route> ALL = List.of(REGISTER_ROUTE, LOGIN_ROUTE);
    }

    public static final class USER {
        public static final String PROFILE = "/user/profile";
        public static final Route PROFILE_ROUTE = new Route(PROFILE, EnumSet.of(Methods.GET, Methods.PUT),
                Set.of(Roles.USER));

        public static final List<Route> ALL = List.of(PROFILE_ROUTE);
    }

    public static final class ADMIN {
        public static final String BASE = "/admin";
        public static final Route BASE_ROUTE = new Route(BASE, EnumSet.of(Methods.GET, Methods.PUT),
                Set.of(Roles.ADMIN));

        public static final List<Route> ALL = List.of(BASE_ROUTE);
    }

    public static final List<Route> PUBLIC_ROUTES = List.of(AUTH.ALL).stream().flatMap(List::stream).toList();
    public static final List<Route> PRIVATE_ROUTES = List.of(USER.ALL, ADMIN.ALL).stream().flatMap(List::stream)
            .toList();
}

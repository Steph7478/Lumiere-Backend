package com.lumiere.infrastructure.security.permissions;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.lumiere.infrastructure.security.config.ApiConfig;
import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.constants.Methods;
import com.lumiere.shared.constants.Roles;

public final class RoutePermissions {

        private static final String API_PREFIX = ApiConfig.API_PREFIX;

        public static final List<RouteRule> PUBLIC_ROUTES = List.of(
                        new RouteRule(API_PREFIX + Routes.Auth.REGISTER, EnumSet.of(Methods.POST), Set.of()),
                        new RouteRule(API_PREFIX + Routes.Auth.LOGIN, EnumSet.of(Methods.POST), Set.of()));

        public static final List<RouteRule> PRIVATE_ROUTES = List.of(
                        new RouteRule(API_PREFIX + Routes.Admin.BASE, EnumSet.of(Methods.GET, Methods.PUT),
                                        Set.of(Roles.ADMIN)));

        public record RouteRule(String path, Set<Methods> methods, Set<Roles> roles) {
        }

        private RoutePermissions() {
        }
}

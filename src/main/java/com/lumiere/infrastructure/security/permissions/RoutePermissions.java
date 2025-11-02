package com.lumiere.infrastructure.security.permissions;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.lumiere.presentation.routes.Routes;
import com.lumiere.shared.constants.Methods;
import com.lumiere.shared.constants.Roles;

public final class RoutePermissions {

        private RoutePermissions() {
        }

        public static final Map<String, Set<Methods>> PUBLIC_ROUTES = Map.of(
                        Routes.Auth.REGISTER, EnumSet.of(Methods.POST),
                        Routes.Auth.LOGIN, EnumSet.of(Methods.POST));

        public static final Map<String, RoutePolicy> PRIVATE_ROUTES = Map.of(
                        Routes.Admin.BASE, new RoutePolicy(
                                        Set.of(Roles.ADMIN),
                                        EnumSet.of(Methods.GET, Methods.PUT)));

        public record RoutePolicy(Set<Roles> roles, Set<Methods> methods) {
        }
}

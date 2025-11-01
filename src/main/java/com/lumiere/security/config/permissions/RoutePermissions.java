package com.lumiere.security.config.permissions;

import com.lumiere.security.constants.Methods;
import com.lumiere.security.constants.Roles;
import com.lumiere.security.constants.Routes;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public final class RoutePermissions {

    private RoutePermissions() {
    }

    public static final Map<String, Set<Methods>> PUBLIC_ROUTES = Map.of(
            Routes.REGISTER, EnumSet.of(Methods.POST),
            Routes.LOGIN, EnumSet.of(Methods.POST));

    public static final Map<String, RoutePolicy> PRIVATE_ROUTES = Map.of(
            Routes.PROFILE, new RoutePolicy(
                    Set.of(Roles.ADMIN), EnumSet.of(Methods.GET, Methods.PUT)));

    public record RoutePolicy(Set<Roles> roles, Set<Methods> methods) {
    }
}

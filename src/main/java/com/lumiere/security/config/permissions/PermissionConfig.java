package com.lumiere.security.config.permissions;

import java.util.Map;
import java.util.Set;

import com.lumiere.security.constants.Methods;
import com.lumiere.security.constants.Roles;
import com.lumiere.security.constants.Routes;

public class PermissionConfig {

        public record Policy(Set<String> roles, Set<String> methods) {
        }

        public static final Map<String, Policy> ROUTES = Map.ofEntries(
                        Map.entry(Routes.REGISTER, new Policy(Set.of(Roles.PUBLIC), Set.of(Methods.POST))),

                        Map.entry(Routes.LOGIN, new Policy(Set.of(Roles.PUBLIC), Set.of(Methods.POST))),

                        Map.entry(Routes.PROFILE,
                                        new Policy(Set.of(Roles.ADMIN, Roles.USER), Set.of(Methods.GET, Methods.PUT))));

        public static Policy getPolicy(String path) {
                return ROUTES.entrySet().stream()
                                .filter(e -> path.startsWith(e.getKey()))
                                .findFirst()
                                .map(Map.Entry::getValue)
                                .orElse(new Policy(Set.of(Roles.PUBLIC), Set.of()));
        }
}

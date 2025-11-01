package com.lumiere.security.config.permissions;

import com.lumiere.security.constants.Methods;
import com.lumiere.security.constants.Roles;
import com.lumiere.presentation.routes.Routes;
import org.springframework.util.AntPathMatcher;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public final class PermissionConfig {

        private PermissionConfig() {
        }

        public record Policy(Set<Roles> roles, Set<Methods> methods) {
                public boolean isMethodAllowed(Methods method) {
                        return methods.isEmpty() || methods.contains(method);
                }

                public boolean isRoleAllowed(Roles role) {
                        return roles.isEmpty() || roles.contains(role);
                }

                public static final Policy DENY_ALL = new Policy(EnumSet.noneOf(Roles.class), Set.of());
        }

        private static final AntPathMatcher pathMatcher = new AntPathMatcher();

        private static final Map<String, Policy> ROUTES = Map.ofEntries(
                        Map.entry(Routes.REGISTER, new Policy(Set.of(), Set.of(Methods.POST))),
                        Map.entry(Routes.LOGIN, new Policy(Set.of(), Set.of(Methods.POST))),
                        Map.entry(Routes.ADMIN, new Policy(EnumSet.of(Roles.ADMIN), Set.of(Methods.GET, Methods.PUT))));

        public static Policy getPolicy(String path) {
                return ROUTES.entrySet().stream()
                                .filter(e -> pathMatcher.match(e.getKey(), path))
                                .map(Map.Entry::getValue)
                                .findFirst()
                                .orElse(Policy.DENY_ALL);
        }
}

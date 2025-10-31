package com.lumiere.security.config.permissions;

import java.util.Map;
import java.util.Set;

public class PermissionConfig {

        public static final String DEFAULT_ROLE = "PUBLIC";

        public record PermissionRule(Set<String> roles, Set<String> methods) {
        }

        public static final Map<String, PermissionRule> ROUTE_PERMISSIONS = Map.of(
                        "/auth/register", new PermissionRule(Set.of(DEFAULT_ROLE), Set.of("POST")));

        public static Set<String> getRolesForRoute(String key) {
                return ROUTE_PERMISSIONS.getOrDefault(key, new PermissionRule(Set.of(DEFAULT_ROLE), Set.of())).roles();
        }

        public static Set<String> getMethodsForRoute(String key) {
                return ROUTE_PERMISSIONS.getOrDefault(key, new PermissionRule(Set.of(DEFAULT_ROLE), Set.of()))
                                .methods();
        }
}

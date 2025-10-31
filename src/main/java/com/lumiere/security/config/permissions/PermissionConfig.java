package com.lumiere.security.config.permissions;

import java.util.Map;
import java.util.Set;

public class PermissionConfig {

        public static final String DEFAULT_ROLE = "PUBLIC";

        public record Policy(Set<String> roles, Set<String> methods) {
        }

        public static final Map<String, Policy> ROUTES = Map.ofEntries(
                        Map.entry("/auth/register", new Policy(Set.of(DEFAULT_ROLE), Set.of("POST"))),
                        Map.entry("/auth/login", new Policy(Set.of(DEFAULT_ROLE), Set.of("POST"))),
                        Map.entry("/users", new Policy(Set.of("ADMIN"), Set.of("GET", "POST", "DELETE"))),
                        Map.entry("/profile", new Policy(Set.of("USER", "ADMIN"), Set.of("GET", "PUT"))));

        public static Policy getPolicy(String path) {
                return ROUTES.entrySet().stream()
                                .filter(e -> path.startsWith(e.getKey()))
                                .findFirst()
                                .map(Map.Entry::getValue)
                                .orElse(new Policy(Set.of(DEFAULT_ROLE), Set.of()));
        }
}

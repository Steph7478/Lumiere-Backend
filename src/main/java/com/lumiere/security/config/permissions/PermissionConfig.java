package com.lumiere.security.config.permissions;

import java.util.Map;
import java.util.Set;

public class PermissionConfig {
        public static final Map<String, PermissionRule> ROUTE_PERMISSIONS = Map.of(
                        "AuthController.registerUser", new PermissionRule(Set.of(), Set.of("POST")));

        public record PermissionRule(Set<String> roles, Set<String> methods) {
        }
}

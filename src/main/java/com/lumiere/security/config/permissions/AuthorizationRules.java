package com.lumiere.security.config.permissions;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpMethod;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AuthorizationRules {

    public record EndpointRule(HttpMethod method, String path, boolean requiresAuth) {
    }

    public static List<EndpointRule> publicEndpoints() {
        return build(false, "PUBLIC");
    }

    public static List<EndpointRule> privateEndpoints() {
        return build(true, "ADMIN", "USER");
    }

    private static List<EndpointRule> build(boolean auth, String... roles) {
        return RoutePermissions.ROUTE_PERMISSIONS.entrySet().stream()
                .flatMap(entry -> Stream.of(roles)
                        .map(entry.getValue()::get)
                        .filter(list -> list != null)
                        .flatMap(List::stream)
                        .distinct()
                        .map(method -> new EndpointRule(method, entry.getKey(), auth)))
                .collect(Collectors.toList());
    }
}

package com.lumiere.security.config.permissions;

import com.lumiere.presentation.routes.Routes;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class RoutePermissions {

        public static final Map<String, Map<String, List<HttpMethod>>> ROUTE_PERMISSIONS = Map.of(
                        Routes.REGISTER, Map.of(
                                        "ADMIN",
                                        List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE),
                                        "PUBLIC", List.of(HttpMethod.POST)),
                        Routes.PRODUCTS, Map.of(
                                        "PUBLIC",
                                        List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)),
                        Routes.ADMIN, Map.of(
                                        "ADMIN",
                                        List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)));
}

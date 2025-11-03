package com.lumiere.infrastructure.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lumiere.infrastructure.security.permissions.RoutePermissions;
import com.lumiere.shared.constants.Methods;
import com.lumiere.shared.constants.Roles;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain) throws ServletException, IOException {

        Methods method = Methods.fromString(req.getMethod());
        String uri = req.getRequestURI();
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (RoutePermissions.PUBLIC_ROUTES.stream().anyMatch(r -> r.path().equals(uri) && r.methods().contains(method))
                || auth == null) {
            chain.doFilter(req, res);
            return;
        }

        if (RoutePermissions.PRIVATE_ROUTES.stream().noneMatch(r -> uri.startsWith(r.path())
                && r.methods().contains(method)
                && auth.getAuthorities().stream().map(a -> Roles.safeOf(a.getAuthority().replace("ROLE_", "")))
                        .flatMap(o -> o.stream())
                        .anyMatch(r.roles()::contains))) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        chain.doFilter(req, res);
    }
}

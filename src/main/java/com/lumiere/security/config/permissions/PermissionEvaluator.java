package com.lumiere.security.config.permissions;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

@Component("permissionEvaluator")
public class PermissionEvaluator {

    public boolean hasPermission(HttpServletRequest request, Authentication auth) {
        String key = request.getRequestURI();
        Set<String> roles = PermissionConfig.getRolesForRoute(key);
        return roles.isEmpty() || auth.getAuthorities().stream()
                .anyMatch(a -> roles.contains(a.getAuthority()));
    }
}

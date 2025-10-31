package com.lumiere.security.config.permissions;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("permissionEvaluator")
public class PermissionEvaluator {

    public boolean hasPermission(Object controller, String methodName, Object method, Authentication auth) {
        String className = controller.getClass().getSimpleName().replaceAll("\\$\\$SpringCGLIB\\$\\$", "");
        String key = className + "." + methodName;

        var rule = PermissionConfig.ROUTE_PERMISSIONS.get(key);
        if (rule == null)
            return false;

        return auth.getAuthorities().stream()
                .anyMatch(a -> rule.roles().contains(a.getAuthority()));
    }
}

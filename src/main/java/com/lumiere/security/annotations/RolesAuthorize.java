package com.lumiere.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@permissionEvaluator.hasPermission(#root.this, #root.method.name, #root.methodMetadata.method, authentication)")
public @interface RolesAuthorize {
}

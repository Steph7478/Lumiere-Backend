package com.lumiere.shared.annotations.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({ ElementType.METHOD, ElementType.MODULE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequireAdminValidator.class)
public @interface RequireAdmin {
    String message() default "User must be an admin";
}

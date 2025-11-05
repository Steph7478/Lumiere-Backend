package com.lumiere.shared.annotations.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEntityGraphPathsValidator.class)
public @interface ValidEntityGraphPaths {

    String message() default "Invalid entity graph path or path not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> root() default Object.class;

    String[] allowedPaths() default {};
}
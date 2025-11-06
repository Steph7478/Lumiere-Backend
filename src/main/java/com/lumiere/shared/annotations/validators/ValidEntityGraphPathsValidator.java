package com.lumiere.shared.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Attribute;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidEntityGraphPathsValidator
        implements ConstraintValidator<ValidEntityGraphPaths, String[]> {

    @PersistenceContext
    private EntityManager em;

    private Class<?> rootEntity;
    private Set<String> allowedPaths;

    @Override
    public void initialize(ValidEntityGraphPaths constraintAnnotation) {
        this.rootEntity = constraintAnnotation.root();
        this.allowedPaths = Arrays.stream(constraintAnnotation.allowedPaths())
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String[] paths, ConstraintValidatorContext context) {
        if (paths == null || paths.length == 0)
            return true;

        final boolean skipMetamodelCheck = rootEntity.equals(Object.class);

        for (String path : paths) {
            if (path == null || path.isBlank())
                continue;

            final String trimmedPath = path.trim();

            if (!allowedPaths.isEmpty() && !allowedPaths.contains(trimmedPath))
                return reject(context, "Path not allowed by security whitelist");

            if (!skipMetamodelCheck && !isValidPath(trimmedPath))
                return reject(context, "Invalid Path");

        }
        return true;
    }

    private boolean isValidPath(String path) {
        try {
            Class<?> current = rootEntity;

            for (String part : path.split("\\.")) {
                EntityType<?> entityType = em.getMetamodel().entity(current);
                Attribute<?, ?> attr = entityType.getAttribute(part);
                current = attr.getJavaType();
            }

            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private boolean reject(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}
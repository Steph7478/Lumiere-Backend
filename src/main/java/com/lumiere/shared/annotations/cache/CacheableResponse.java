package com.lumiere.shared.annotations.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface CacheableResponse {
    long maxAgeInSeconds() default 3600;

    long sMaxAgeInSeconds() default -1;

    boolean noStore() default false;
}
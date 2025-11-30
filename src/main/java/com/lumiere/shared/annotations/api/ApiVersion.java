package com.lumiere.shared.annotations.api;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {
    String[] value();
}

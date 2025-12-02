package com.lumiere.infrastructure.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

@Component("sortedSetKeyGenerator")
public class SortedSetKeyGenerator implements KeyGenerator {

    @SuppressWarnings("null")
    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length > 0 && params[0] instanceof Collection) {
            Collection<?> collection = (Collection<?>) params[0];
            return collection.stream()
                    .map(Object::toString)
                    .sorted()
                    .collect(Collectors.joining("_"));
        }
        return "no-collection";
    }
}
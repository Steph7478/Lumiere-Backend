package com.lumiere.presentation.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

    @Around("@annotation(com.lumiere.shared.annotations.Loggable)")
    public Object logControllerExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            System.out.println("\n[CONTROLLER LOG] " + joinPoint.getSignature() +
                    " executed in " + elapsed + "ms | Result: " + result);
            return result;
        } catch (Throwable ex) {
            System.out.println("\n[CONTROLLER ERROR] " + joinPoint.getSignature() +
                    " threw " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            throw ex;
        }
    }
}

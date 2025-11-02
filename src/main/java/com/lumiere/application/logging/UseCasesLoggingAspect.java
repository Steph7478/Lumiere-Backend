package com.lumiere.application.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UseCasesLoggingAspect {

    @Before("@annotation(com.lumiere.shared.annotations.Loggable)")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[USECASE LOG] Entering: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.lumiere.application.usecases..*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out
                .println("[USECASE LOG] Exiting: " + joinPoint.getSignature().toShortString() + " | Result: " + result);
    }
}

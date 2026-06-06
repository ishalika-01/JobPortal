package com.ishalika.SpringJobPortalRevisionJWT.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.ishalika.SpringJobPortalRevisionJWT.service.JobService.*(..))")
    public void jobServiceMethods() {
    }

    @Before("jobServiceMethods()")
    public void logMethodCall(JoinPoint jp) {
        LOGGER.info(
                "event=service_method_start class={} method={} args={}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                Arrays.toString(jp.getArgs())
        );
    }

    @AfterReturning(pointcut = "jobServiceMethods()", returning = "result")
    public void logMethodExecutedSuccess(JoinPoint jp, Object result) {
        LOGGER.info(
                "event=service_method_success class={} method={} result={}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                result
        );
    }

    @AfterThrowing(pointcut = "jobServiceMethods()", throwing = "ex")
    public void logMethodCrashed(JoinPoint jp, Exception ex) {
        LOGGER.error(
                "event=service_method_error class={} method={} exception={} message={}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
    }
}

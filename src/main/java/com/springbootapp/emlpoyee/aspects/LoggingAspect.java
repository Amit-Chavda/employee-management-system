package com.springbootapp.emlpoyee.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    private final HttpServletRequest request;

    public LoggingAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Pointcut("execution(* com.springbootapp.emlpoyee.controller.*.*(..))")
    public void loggingPointcut() {
    }

    @AfterReturning(value = "loggingPointcut()", returning = "returnedObject")
    public void afterReturning(JoinPoint joinPoint, Object returnedObject) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(request.getRequestURI() + " accessed by " + username);
    }

    @AfterThrowing("execution(* com.springbootapp.emlpoyee.service.*.*(..))")
    public void afterThrowing(JoinPoint joinPoint) {
        log.error("Exception thrown by " + joinPoint.getSignature() + " ");
    }

}

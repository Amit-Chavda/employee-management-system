package com.springbootapp.emlpoyee.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GeneralLoggingAspect {

	private Logger logger = LoggerFactory.getLogger(GeneralLoggingAspect.class);

	@Pointcut("execution(* com.springbootapp.emlpoyee.controller.*.*(..))")
	public void loggingPointcut() {
	}

	@Before("loggingPointcut()")
	public void beforeRequest(JoinPoint joinPoint) {
		logger.info("Before method invoked :" + joinPoint.getSignature());
	}

	@After("loggingPointcut()")
	public void afterRequest(JoinPoint joinPoint) {
		logger.info("After method invoked :" + joinPoint.getSignature());
	}
}

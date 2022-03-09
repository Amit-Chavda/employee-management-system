package com.springbootapp.emlpoyee.aspects;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.springbootapp.emlpoyee.entity.Employee;

@Aspect
@Component
public class GeneralLoggingAspect {

	private Logger logger = LoggerFactory.getLogger(GeneralLoggingAspect.class);

	@Pointcut("execution(* com.springbootapp.emlpoyee.controller.*.*(..))")
	public void loggingPointcut() {
	}

	@Before("loggingPointcut()")
	public void beforeAdvice(JoinPoint joinPoint) {
		logger.info("Before method invoked : " + joinPoint.getSignature());
	}

	@After("loggingPointcut()")
	public void afterAdvice(JoinPoint joinPoint) {
		logger.info("After method invoked : " + joinPoint.getSignature());
	}

//	@Around("execution(* com.springbootapp.emlpoyee.service.*.*(..))")
//	public List<Employee> aroundAdvcice(ProceedingJoinPoint joinPoint) {
//		logger.info("Around Before method invoked : " + joinPoint.getSignature());
//		List<Employee> employees = null;
//		try {
//			employees = (List<Employee>) joinPoint.proceed();
//			logger.info("Response : " + employees.toString());
//		} catch (Throwable e) {
//			logger.error(e.getMessage());
//		}
//		logger.info("Around After method invoked : " + joinPoint.getSignature());
//
//		return employees;
//	}

	@AfterReturning("loggingPointcut()")
	public void afterReturning(JoinPoint joinPoint) {
		logger.info("Method returned succcessfully : " + joinPoint.getSignature());
	}

	@AfterThrowing("execution(* com.springbootapp.emlpoyee.service.*.*(..))")
	public void afterThrowing(JoinPoint joinPoint) {
		logger.error("Exception thrown by " + joinPoint.getSignature() + " ");
	}

}

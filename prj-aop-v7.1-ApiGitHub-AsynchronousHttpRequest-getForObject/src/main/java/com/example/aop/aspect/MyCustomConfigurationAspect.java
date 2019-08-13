package com.example.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


/*
 * https://dzone.com/articles/implementing-aop-with-spring-boot-and-aspectj
 */

@Aspect
@Configuration
public class MyCustomConfigurationAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger( MyCustomConfigurationAspect.class );
	
	@Around("@annotation(com.example.aop.aspect.TimeTracking)")	
	public void around(ProceedingJoinPoint joinPoint ) throws Throwable {
		LOGGER.debug( " ---------- Inside AROUND ------------ ");
		long startTime = System.currentTimeMillis();
		
		joinPoint.proceed();
		
		long timeTaking = System.currentTimeMillis() - startTime;
		LOGGER.debug( "  ---------- Time taken by {} is {} ", joinPoint, timeTaking );				
	}
	
	
	// @Before("execution(* com.example.easynotes.aspect.UserAccess.*.*(..))")
	@Before("@annotation(com.example.aop.aspect.UserAccess)")	
	public void before(JoinPoint joinPoint) throws Throwable {
		LOGGER.debug( " +++++++++ get Inside BEFORE getUserAccess ------------ ");
		System.out.println(" +++++++++ invoked methodName = " + joinPoint.getSignature().getName() );
		LOGGER.debug( " +++++++++ get Outside BEFORE getUserAccess ------------ ");
	}
	
}

/*
 
Notes:

@Around uses an around advice. It intercepts the method call and uses joinPoint.proceed() to execute the method.
@annotation(com.in28minutes.springboot.tutorial.basics.example.aop.TrackTime) is the pointcut to define interception based on an annotation — @annotation followed by the complete type name of the annotation.
Once we define the annotation and the advice, we can use the annotation on methods that we would want to track, as shown below:

*/
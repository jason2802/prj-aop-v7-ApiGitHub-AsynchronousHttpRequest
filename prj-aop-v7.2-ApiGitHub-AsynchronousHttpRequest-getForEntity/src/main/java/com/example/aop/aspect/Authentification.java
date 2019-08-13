package com.example.aop.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//  https://blogs.ashrithgn.com/2018/07/19/custom-authorization-annotation/
	
// This interface will help to implement AOP Authentication and Autorisation - that means without Spring Security

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention (RetentionPolicy.RUNTIME)
public @interface Authentification {
	//public boolean enabled default true;
}

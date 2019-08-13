package com.example.aop.service;

import org.springframework.stereotype.Component;

import com.example.aop.aspect.Logging;

@Component
public class AuthorizationImpl {
	
	@Logging
	public boolean authorize(String token) {
        
		// implement jwt or any token based authorization logic ( esemple oAuth ) 
		
        return token.equalsIgnoreCase( "132-token-123" );
        
    }
	
}

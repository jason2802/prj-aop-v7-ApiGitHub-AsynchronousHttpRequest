package com.example.aop.controller;



import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.aop.aspect.Authentification;
import com.example.aop.model.User;
import com.example.aop.service.UserService;

@RestController
@RequestMapping("/authentication")
public class AuthorizationController {
		
	private final UserService userService;
	
	@Inject
	public AuthorizationController( UserService userService ) {
		this.userService = userService;
	}
	
	@Authentification
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public @ResponseBody User userService( HttpServletRequest request ){	
		String contentType = request.getContentType();
		System.out.print( " +++++++++++++++++++++ contentType " + contentType );
		return userService.userDetails( "1" );		
	}	
	
}

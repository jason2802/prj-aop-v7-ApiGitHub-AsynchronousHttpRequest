package com.example.aop.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.aop.model.User_ApiGitHub;

@RestController
@RequestMapping( value = "/api")
public class ApiGitHubController {

	RestTemplate restTemplate = new RestTemplate();
			
	@RequestMapping( value = "/github/{username}")
	User_ApiGitHub getUser(@PathVariable String username) {
		User_ApiGitHub user_ApiGitHub = restTemplate.getForObject( "https://api.github.com/users/{username}", User_ApiGitHub.class, username );
		return user_ApiGitHub;
	}
	
}

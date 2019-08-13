package com.example.aop.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.aop.configuration.RestService;
import com.example.aop.model.User_ApiGitHub;

@RestController
@RequestMapping( value = "/api")
public class ApiGitHubController {

	RestTemplate restTemplate = new RestTemplate();
		
	private RestService restService;
	
	@Inject
	public ApiGitHubController( RestService restService) {
		this.restService = restService;
	}
	
	
	/*
	 * Downside : getForObject doesn't return HTTP Headers
	 * 
	 * */	
	@RequestMapping( value = "/github/{username}")
	User_ApiGitHub getUser(@PathVariable String username) {
		User_ApiGitHub user_ApiGitHub = restTemplate.getForObject( restService.getGithubEndpoint() + "/{username}", User_ApiGitHub.class, username );
		return user_ApiGitHub;
	}
	
	
	
}

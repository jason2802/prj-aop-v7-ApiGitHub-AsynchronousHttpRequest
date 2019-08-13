package com.example.aop.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aop.aspect.Logging;
import com.example.aop.model.Organizations;
import com.example.aop.model.Repositories;
import com.example.aop.model.User_ApiGitHub;
import com.example.aop.service.GitHubClient;

@RestController
@RequestMapping( value = "/api")
public class ApiGitHubController {
			
	private GitHubClient gitHubClient;
	
	@Inject
	public ApiGitHubController( GitHubClient gitHubClient ) {
		this.gitHubClient = gitHubClient;
	}
	
	
	@Logging
	@RequestMapping( value = "/github/{username}")
	ResponseEntity<User_ApiGitHub> getUser(@PathVariable String username) throws Exception {
				
		/*
		 The controller that now uses the GitHubClient class to make HTTP
			requests. Methods getUser(String username), getRepos(String username) and getOrganizations(
			String username) are called one after another without waiting for HTTP request to be
			completed. After all three HTTP requests are initiated, static allOf method is being called with
			all three CompletableFutures.
		 */
		
		HttpHeaders headers = new HttpHeaders();
		StopWatch stopwatch = new StopWatch();
		stopwatch.start();
		
		CompletableFuture<User_ApiGitHub> cf_UserFromApiGitHub = gitHubClient.getUser( username );		
		CompletableFuture<List<Repositories>> cf_RepositoriesFromApiGitHub = gitHubClient.getRepo( username );
		CompletableFuture<List<Organizations>> cf_OrganisationsFromApiGitHub = gitHubClient.getOrg( username );
		CompletableFuture.allOf( cf_UserFromApiGitHub, cf_RepositoriesFromApiGitHub, cf_OrganisationsFromApiGitHub ).join();
		
		User_ApiGitHub user = cf_UserFromApiGitHub.get();
		user.setRepositories( cf_RepositoriesFromApiGitHub.get() );
		user.setOrganizations( cf_OrganisationsFromApiGitHub.get() );
		
		stopwatch.stop();
		
		return new ResponseEntity<>( user, headers, HttpStatus.OK );
		
	}	
	
}

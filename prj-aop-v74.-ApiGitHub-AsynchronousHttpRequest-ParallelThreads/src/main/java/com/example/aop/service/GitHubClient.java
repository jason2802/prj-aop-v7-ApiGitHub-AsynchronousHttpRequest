package com.example.aop.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.aop.configuration.RestServiceConfig;
import com.example.aop.model.Organizations;
import com.example.aop.model.Repositories;
import com.example.aop.model.User_ApiGitHub;

@Service
public class GitHubClient {
	
	/* MES URL vers mon compte GITHUB
	 * 
	 * https://github.com/jason2802/jason-repository-2019: 
	 * 
	 * https://api.github.com/users/jason2802
	 * https://api.github.com/users/jason2802/repos 
	 * https://api.github.com/users/jason2802/orgs
	 * 
	 * 
	 * */
	
	RestTemplate restTemplate = new RestTemplate();
	
	private RestServiceConfig restServiceConfig;
	
	@Inject
	public GitHubClient( RestServiceConfig restServiceConfig) {
		this.restServiceConfig = restServiceConfig;
	}	
	
	
	@Async
	public CompletableFuture<User_ApiGitHub> getUser( String username ) throws Exception {		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		
		ResponseEntity<User_ApiGitHub> user_ApiGitHub = restTemplate.getForEntity( restServiceConfig.getGithubBaseEndpoint() + "/{username}", User_ApiGitHub.class, username );
		
		user_ApiGitHub.getHeaders().entrySet()
								   .forEach( e -> { 
									   					if (!( e.getKey().equalsIgnoreCase( HttpHeaders.CONTENT_LENGTH ))) {
									   						responseHeaders.addAll( e.getKey(), e.getValue() );
									   					}									   				
								   					});
		Thread.sleep(2000L);
		return CompletableFuture.completedFuture( user_ApiGitHub.getBody() );
				
	}
	
	@Async
	public CompletableFuture<List<Repositories>> getRepo( String username ) throws Exception {
		
		String url = restServiceConfig.getGithubBaseEndpoint() + "/{username}" + restServiceConfig.getGithubSuffixRepo();
		
		Repositories[] user_ApiGitHub_Repositories = restTemplate.getForObject( url , Repositories[].class, username );
		
		Thread.sleep(2000L);
		
		return CompletableFuture.completedFuture( Arrays.asList( user_ApiGitHub_Repositories ) );
						
	}
	
	@Async
	public CompletableFuture<List<Organizations>> getOrg( String username ) throws Exception {
		
		String url = restServiceConfig.getGithubBaseEndpoint() + "/{username}" + restServiceConfig.getGithubSuffixOrg();
		
		Organizations[] user_ApiGitHub_Organizations = restTemplate.getForObject( url , Organizations[].class, username );
		
		Thread.sleep(2000L);
		
		return CompletableFuture.completedFuture( Arrays.asList( user_ApiGitHub_Organizations ) );
						
	}
	
	
}

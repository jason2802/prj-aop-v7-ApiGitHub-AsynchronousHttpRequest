package com.example.aop.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "restservice")
public class RestService {
		
	private String githubEndpoint;

	public void setGithubEndpoint( String githubEndpoint ) {
		this.githubEndpoint = githubEndpoint;
	}

	public String getGithubEndpoint() {
		return githubEndpoint;
	}

	
}

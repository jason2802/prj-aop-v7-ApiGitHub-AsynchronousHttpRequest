package com.example.aop.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Component
@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "restservice.users")
public class RestServiceConfig {
		
	private String githubBaseEndpoint;

	private String githubSuffixRepo;
	
	private String githubSuffixOrg;
	
}

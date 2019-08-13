package com.example.aop.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User_ApiGitHub {
	
	private String id;
	private String login;
	private String location;
	private int open_issues;
	private String node_id;
	
	private List<Repositories> repositories;
	private List<Organizations> organizations;
	

}

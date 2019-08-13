package com.example.aop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repositories {
	private String name;
	private String description;
	private String html_url;
	private String url;
	private int public_repos;
	private String default_branch;
	private String clone_url;
}

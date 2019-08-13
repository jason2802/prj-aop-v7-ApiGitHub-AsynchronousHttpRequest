package com.example.aop.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.example.aop.model.User;
import com.example.aop.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Inject
	public UserService( UserRepository userRepository ) {
		this.userRepository = userRepository;
	}
	
	public User userDetails( String userId) {
				
		return userRepository.getUserDetails( userId );	
		
	}
}

package com.example.aop.repository;

import org.springframework.stereotype.Repository;

import com.example.aop.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

	public User getUserDetails( String userdId ) {
		
		User user = new User();
		user.setId(Long.parseLong( userdId ));
		user.setName("Paul");
		user.setEmail("paul@email.com");
		
		return user;
	}
}

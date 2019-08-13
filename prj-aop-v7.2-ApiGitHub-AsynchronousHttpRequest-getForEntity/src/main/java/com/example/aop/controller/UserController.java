package com.example.aop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aop.aspect.Logging;
import com.example.aop.exception.UserNotFoundException;
import com.example.aop.model.User;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Logging
    @GetMapping("/{username}")
    public ResponseEntity<User> get(@PathVariable String username) throws UserNotFoundException {
        
    	// More logic on User
        throw UserNotFoundException.createWith(username);
    }
}

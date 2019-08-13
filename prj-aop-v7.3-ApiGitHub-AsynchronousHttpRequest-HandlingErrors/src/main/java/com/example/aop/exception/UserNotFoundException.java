package com.example.aop.exception;

public class UserNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
	
    private String username;

    public static UserNotFoundException createWith(String username) {
        return new UserNotFoundException(username);
    }

    private UserNotFoundException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "User '" + username + "' not found";
    }
}
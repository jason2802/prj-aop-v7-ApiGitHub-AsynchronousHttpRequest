package com.example.aop.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class ContentNotAllowedException extends Exception{

	private static final long serialVersionUID = 1L;
	
	List<ObjectError> errors;
	
	public static ContentNotAllowedException createWith( List<ObjectError> errors ) {
		return new ContentNotAllowedException( errors );
	}
	
	private ContentNotAllowedException( List<ObjectError> errors ) {
		this.errors = errors;
	}
	
	public List<ObjectError> getErrors() {
		return errors;
	}
 
}

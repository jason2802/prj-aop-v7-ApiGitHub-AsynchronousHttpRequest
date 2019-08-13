package com.example.aop.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.validation.ObjectError;

import com.example.aop.model.Containable;


public class ContentUtils {
	
    private static final List<String> NOT_ALLOWED_WORDS = Arrays.asList(
            "politics",
            "terrorism",
            "murder"
    );

    public static List<ObjectError> getContentErrorsFrom(Containable containable) {
    	
    	
    	return Arrays.asList( containable.getContent().split( "\\s" ) )
    			.stream()
    			.filter( NOT_ALLOWED_WORDS::contains )
    			.map( notAllowedWord -> new ObjectError( notAllowedWord, " not allowed word " ) )
    			.collect( Collectors.toList() );
    	
    					/*
					    	Object a = NOT_ALLOWED_WORDS.stream()
					    					.filter( e -> e.equalsIgnoreCase( containable.getContent() ) )
					    					.map( notFound -> new ObjectError( notFound, " not found" ))
					    					.collect( Collectors.toList() );
					    					
					    					// .map( String::contains )
					    					// .
					    					
    					 */
    	
    }
}
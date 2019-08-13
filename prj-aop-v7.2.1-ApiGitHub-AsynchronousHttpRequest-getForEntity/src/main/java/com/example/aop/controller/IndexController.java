package com.example.aop.controller;


import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.aop.aspect.Authentification;
import com.example.aop.model.Note;

/*
 *  https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/
 */

@Authentification
@RestController
@RequestMapping("/index")
public class IndexController {
	
	
	@GetMapping("/hello")
    public String sayHello() {
        return "Hello and Welcome to the EasyNotes application. You can create a new Note by making a POST request to /api/notes endpoint.";
    }
	
	
	@RequestMapping(value="/salut", method=RequestMethod.GET)
    public @ResponseBody String saySalut( @Valid @RequestBody Note note ) {
        return "Hello and Welcome to the EasyNotes application. You can create a new Note by making a POST request to /api/notes endpoint.";
    }
	
}

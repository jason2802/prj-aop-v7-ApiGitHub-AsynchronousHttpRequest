package com.example.aop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aop.aspect.Logging;
import com.example.aop.common.ContentUtils;
import com.example.aop.exception.ContentNotAllowedException;
import com.example.aop.model.Post;

@RestController
@RequestMapping("/users/{username}/posts")
public class PostController {

	@Logging
    @PostMapping
    public ResponseEntity<Post> create(@PathVariable String username, @RequestBody Post post) throws ContentNotAllowedException {
        
    	List<ObjectError> contentNotAllowedErrors = ContentUtils.getContentErrorsFrom(post);

        if (!contentNotAllowedErrors.isEmpty()) {
            throw ContentNotAllowedException.createWith(contentNotAllowedErrors);
        }

        // More logic on Post

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

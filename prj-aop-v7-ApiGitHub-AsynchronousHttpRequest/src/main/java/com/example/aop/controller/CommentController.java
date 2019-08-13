package com.example.aop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aop.aspect.Logging;
import com.example.aop.common.ContentUtils;
import com.example.aop.exception.ContentNotAllowedException;
import com.example.aop.model.ApiError;
import com.example.aop.model.Comment;

@RestController
@RequestMapping("/users/{username}/posts/{post_id}/comments")
public class CommentController {
	
	@Logging
    @PostMapping
    public ResponseEntity<Comment> create(	@PathVariable String username,
                                          	@PathVariable(name = "post_id") Long postId,
                                          	@RequestBody Comment comment  )  throws ContentNotAllowedException{
    	
        List<ObjectError> contentNotAllowedErrors = ContentUtils.getContentErrorsFrom(comment);

        if (!contentNotAllowedErrors.isEmpty()) {
            throw ContentNotAllowedException.createWith(contentNotAllowedErrors);
        }

        // More logic on Comment

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
	
	@Logging
    @ExceptionHandler(ContentNotAllowedException.class)
    public ResponseEntity<ApiError> handleContentNotAllowedException(	ContentNotAllowedException cnae	) {
    	
        List<String> errorMessages = cnae.getErrors()
                .stream()
                .map(contentError -> contentError.getObjectName() + " " + contentError.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>( new ApiError(errorMessages), HttpStatus.BAD_REQUEST );
    }
}

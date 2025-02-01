package com.gugucoding.restful.review.controller.advice;

import com.gugucoding.restful.review.exception.ReviewException;
import com.gugucoding.restful.review.exception.ReviewTaskExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ReviewControllerAdvice {

    @ExceptionHandler(ReviewTaskExceptions.class)
    public ResponseEntity<Map<String,String>> ReviewExceptionAdvice(ReviewTaskExceptions reviewTaskExceptions){
         int status = reviewTaskExceptions.getCode();
         String  msg = reviewTaskExceptions.getMessage();

        return ResponseEntity.status(status).body(Map.of("error",msg));
    }


}

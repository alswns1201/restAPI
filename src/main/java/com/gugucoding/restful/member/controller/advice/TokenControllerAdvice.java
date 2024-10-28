package com.gugucoding.restful.member.controller.advice;


import com.gugucoding.restful.member.exception.MemberTaskException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Log4j2
public class TokenControllerAdvice {

    @ExceptionHandler(MemberTaskException.class)
    public ResponseEntity<Map<String,String>> handleTaskException(MemberTaskException ex){
        log.error(ex.getMessage());

        String msg = ex.getMsg();
        int status = ex.getCode();

        Map<String, String> map = Map.of("Error",msg);
        return ResponseEntity.status(status).body(map);
    }

}

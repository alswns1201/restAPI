package com.gugucoding.restful.member.controller.advice;


import com.gugucoding.restful.member.exception.MemberTaskException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
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

    /*
        접근 제한에 대한 ERROR 처리 로직.
        @PreAuthorize("hasAnyRole('ROLE_ADMIN')") 로 접근 제한 시 해당 경로로 들어와 Return 된다.

     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAcessDeniedException(AccessDeniedException exception){

        log.info("handleDeniedExeption ......................");

        Map<String,Object> errors = new HashMap<>();
        errors.put("message",exception.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);

    }

}

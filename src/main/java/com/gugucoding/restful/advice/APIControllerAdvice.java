package com.gugucoding.restful.advice;


import com.gugucoding.restful.exception.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class APIControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleArgsException(MethodArgumentNotValidException exception){
        Map<String, Object> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
            errors.put(fieldError.getField(),fieldError.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleArgsException(MethodArgumentTypeMismatchException exception){
        Map<String, Object> errors = new HashMap<>();
        errors.put("error","Type Mismatch");
        errors.put(exception.getName(),exception.getValue()+"is not valid value");
        return  new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    /**
     * Not Found 는 내부 로직의 문제로서  500
     * @param exception
     * @return
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception){
        Map<String,String> errors = Map.of("message","Entity Not Found");
        return new ResponseEntity<>(errors,HttpStatus.NOT_FOUND);
    }

}

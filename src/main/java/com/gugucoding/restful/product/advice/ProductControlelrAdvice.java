package com.gugucoding.restful.product.advice;

import com.gugucoding.restful.product.exception.ProductTaskException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class ProductControlelrAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidException(MethodArgumentNotValidException e){
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String errMessage = errors.stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(Map.of("error",errMessage));
    }

    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<Map<String,String>> productTaskException(ProductTaskException e){
        int status= e.getCode();
        return ResponseEntity.status(status).body(Map.of("error",e.getMessage()));
    }

}

package com.gugucoding.restful.sample.controller;


import com.gugucoding.restful.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sample")
@Log4j2
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")  // ADMIN 권한 사용자만 접근 가능 .
    @GetMapping("/list")
    public ResponseEntity<?> list(){
        String[] arr = {"AAA","BBB","CCC"};

        return ResponseEntity.ok(arr);
    }

    @RequestMapping("/hello")
    public String[] hell(){
        return new String[]{"Hello","World"};
    }

}

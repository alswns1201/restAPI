package com.gugucoding.restful.controller;


import com.gugucoding.restful.dto.PageRequestDTO;
import com.gugucoding.restful.dto.TodoDTO;
import com.gugucoding.restful.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/todos")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
/*
    @PostMapping("/bindingResult")
    public ResponseEntity<TodoDTO> register(@RequestBody @Validated TodoDTO todoDT, BindingResult bindingResult){

        log.info("BindingReuslt 객체를 이용한 validate 처리 resgister-------------------");
        if(bindingResult.hasErrors()){
            log.info(bindingResult.getAllErrors());
            return ResponseEntity.badRequest().build();
        }
        return  null;
    }
 */

    @PostMapping("")
    public ResponseEntity<TodoDTO> register(@RequestBody @Validated TodoDTO todoDTO) {

        log.info("register-------------------------");
        log.info(todoDTO);

        todoDTO.setMno(null);

        return ResponseEntity.ok(todoService.register(todoDTO));
    }

    @GetMapping("/{mno}")
    public ResponseEntity<TodoDTO> read(@PathVariable("mno") Long mno) {
        log.info("read...........");
        log.info(mno);

        return ResponseEntity.ok(todoService.read(mno));
    }


    @PutMapping("/{mno}")
    public ResponseEntity<TodoDTO> modify(@PathVariable("mno") Long mno, @RequestBody TodoDTO todoDTO) {
        log.info("modify...........");
        log.info(mno);
        todoDTO.setMno(mno);
        TodoDTO modifyDTO = todoService.change(todoDTO);


        return ResponseEntity.ok(modifyDTO);
    }

    @DeleteMapping("/{mno}")
    public ResponseEntity<Map<String,String>> remove(@PathVariable("mno") Long mno) {
        log.info("remove...........");
        todoService.remove(mno);
        Map<String,String > result = Map.of("result","success");

        return ResponseEntity.ok(result);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<TodoDTO>> list(@Validated PageRequestDTO pageRequestDTO) {
        log.info("page List...........");

        return ResponseEntity.ok(todoService.getList(pageRequestDTO));
    }

}

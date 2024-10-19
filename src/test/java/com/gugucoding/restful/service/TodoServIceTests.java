package com.gugucoding.restful.service;


import com.gugucoding.restful.dto.PageRequestDTO;
import com.gugucoding.restful.dto.TodoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@SpringBootTest
public class TodoServIceTests {

        @Autowired
        private TodoService todoService;

        public void TodoDto저장(){
            TodoDTO todoDTO = new TodoDTO();
            todoDTO.setTitle("dto");
            todoDTO.setWriter("M");
            todoDTO.setDueDate(LocalDate.of(2024,10,12));

            TodoDTO resultDto = todoService.register(todoDTO);
        }

        public void 읽기(){
            Long mno = 1L;
            TodoDTO todoDTO = todoService.read(mno);
        }

        public void 페이지테스트(){
            PageRequestDTO pageRequestDTO = new PageRequestDTO();
            Page<TodoDTO> result =  todoService.getList(pageRequestDTO);

        }

}

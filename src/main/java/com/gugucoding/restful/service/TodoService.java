package com.gugucoding.restful.service;

import com.gugucoding.restful.dto.PageRequestDTO;
import com.gugucoding.restful.dto.TodoDTO;
import com.gugucoding.restful.entity.TodoEntity;
import com.gugucoding.restful.exception.EntityNotFoundException;
import com.gugucoding.restful.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoDTO register(TodoDTO todoDTO){

        TodoEntity todoEntity = todoDTO.todoEntity(); // 실제 객체로 변환

        todoRepository.save(todoEntity); // 변환후 영속성 컨텍스트에 반영.

        return  new TodoDTO(todoEntity);
    }

    public TodoDTO read(Long mno){

        Optional<TodoDTO> result =  todoRepository.getDTO(mno);
         // TodoDTO todoDTO =  result.orElseThrow();
        // 컨트롤러 입장에서 본다면 서비스에서 예외를 처리하는것은 바람직 하지 않다.
        TodoDTO todoDTO = result.orElseThrow(()->
           new EntityNotFoundException("todo not found")
        );
        return  todoDTO;
    }

    public void remove(Long mno){
        Optional<TodoEntity> result = todoRepository.findById(mno);
        TodoEntity todoEntity =  result.orElseThrow(()->
                new EntityNotFoundException("todo not found"));
        todoRepository.delete(todoEntity);
    }

    public TodoDTO change(TodoDTO todoDTO){
        Optional<TodoEntity> result = todoRepository.findById(todoDTO.getMno());
        TodoEntity todoEntity =  result.orElseThrow(()->
                new EntityNotFoundException("todo not found"));
        todoEntity.changeTitle(todoDTO.getTitle());
        todoEntity.changeWrite(todoDTO.getWriter());
        todoEntity.changeDueDate(todoDTO.getDueDate());
        return new TodoDTO(todoEntity);
    }

    public Page<TodoDTO> getList(PageRequestDTO pageRequestDTO){

        Sort sort = Sort.by("mno").descending();
        Pageable pageable = pageRequestDTO.getPageable(sort);
        return todoRepository.searchDTO(pageable);


    }



}

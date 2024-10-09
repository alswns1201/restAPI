package com.gugucoding.restful.repository;


import com.gugucoding.restful.dto.TodoDTO;
import com.gugucoding.restful.entity.TodoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;


    @Test
    public void 레파지토리저장테스트(){
        TodoEntity todoEntity
                = TodoEntity.builder()
                .title("부트save")
                .writer("who")
                .dueDate(LocalDate.of(2024,10,06))
                .build();
        todoRepository.save(todoEntity);

        //더미 테스트
        for(int i = 0; i < 100; i ++){
            TodoEntity todo = TodoEntity.builder() .title("부트save")
                    .writer("who")
                    .dueDate(LocalDate.of(2024,10,06)).build();
            todoRepository.save(todoEntity);
        }
    }
    @Test
    public void 레파지토리read테스트(){
        Long mno = 58L;

        Optional<TodoEntity> result = todoRepository.findById(mno);

        result.ifPresent(todoEntity -> {System.out.println(todoEntity);});

    }

    @Test
    public void 더티체크확인(){
        Optional<TodoEntity> entity = todoRepository.findById(1L);

        TodoEntity result = entity.get();
        result.changeTitle("changeTitle"); // entity를 바꿔주기만 해도 더킹체크를 해서 적용됨.
        //save를 해주지 않아도 update 가 진행된다.
    }

    @Test
    public void 삭제테스트(){

        todoRepository.deleteById(1l);//알아서 영속성컨텍스트 에서 조회후에 삭제해준다.
    }

    @Test
    public void 페이징테스트(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno")
                .descending());
        Page<TodoEntity> result = todoRepository.findAll(pageable);
        Page<TodoEntity> result2 = todoRepository.listAll(pageable);

        List<TodoEntity> todoEntityList = result.getContent();
    }

    @Test
    public void Q클래스테스트(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());

        Page<TodoEntity> result = todoRepository.search1(pageable);

        List<TodoEntity> todoEntityList = result.getContent();
        todoEntityList.forEach(todoEntity -> {
            System.out.println(todoEntity);
        });

    }

    @Test
    public void testGetTodoDTO(){
        long mno = 59L;
        Optional<TodoDTO> result = todoRepository.getDTO(mno);
        result.ifPresent(todoDTO -> {System.out.println(todoDTO);});
    }



}

package com.gugucoding.restful.dto;

import com.gugucoding.restful.entity.TodoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TodoDTO {

    private Long mno;
    private String title;
    private String writer;
    private LocalDate dueDate;

    public TodoDTO(TodoEntity todoEntity){
        this.mno = todoEntity.getMno();
        this.title = todoEntity.getTitle();
        this.writer = todoEntity.getWriter();
        this.dueDate = todoEntity.getDueDate();
    }
}

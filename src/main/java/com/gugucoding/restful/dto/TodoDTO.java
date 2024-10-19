package com.gugucoding.restful.dto;

import com.gugucoding.restful.entity.TodoEntity;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TodoDTO {

    private Long mno;
    @NotEmpty
    private String title;
    @NotEmpty
    private String writer;

    @FutureOrPresent
    private LocalDate dueDate;

    public TodoDTO(TodoEntity todoEntity){
        this.mno = todoEntity.getMno();
        this.title = todoEntity.getTitle();
        this.writer = todoEntity.getWriter();
        this.dueDate = todoEntity.getDueDate();
    }

    public TodoEntity todoEntity(){
        return TodoEntity.builder()
                .mno(mno)
                .title(title)
                .writer(writer)
                .dueDate(dueDate)
                .build();
    }
}

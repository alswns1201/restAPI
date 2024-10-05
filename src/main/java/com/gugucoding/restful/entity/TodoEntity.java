package com.gugucoding.restful.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tbl_todos")
@Builder
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 500,nullable = false)
    private String title;

    @Column(length = 500,nullable = false)
    private String writer;


    private LocalDate dueDate;



}

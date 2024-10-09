package com.gugucoding.restful.repository.search;

import com.gugucoding.restful.dto.TodoDTO;
import com.gugucoding.restful.entity.TodoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoSearch {

    Page<TodoEntity> search1(Pageable pageable);

    Page<TodoDTO> searchDTO(Pageable pageable);
}

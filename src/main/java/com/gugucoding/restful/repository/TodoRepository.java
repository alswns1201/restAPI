package com.gugucoding.restful.repository;

import com.gugucoding.restful.dto.TodoDTO;
import com.gugucoding.restful.entity.TodoEntity;
import com.gugucoding.restful.repository.search.TodoSearch;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoEntity,Long>, TodoSearch {



//    @Query("select t from TodoEntity t ")
    @Query( value = "select * from tbl_todos t ",
            countQuery = " select * from tbl_todos",
            nativeQuery = true)
    Page<TodoEntity> listAll(Pageable pageable);

    @Query("select t from TodoEntity t " +
            "where t.title  like %:keyword% and t.mno > 0 order by t.mno desc")
    Page<TodoEntity> listOfTile(@Param("keyword")String keyword, Pageable  pageable);

    /**
     * Entity를 이용하지 않고 DTO를 이용 해서 사용.
     * @param mno
     * @return
     */
    @Query(" SELECT t from TodoEntity t where t.mno =:mno")
    Optional<TodoDTO> getDTO(@Param("mno")Long mno);

    
}

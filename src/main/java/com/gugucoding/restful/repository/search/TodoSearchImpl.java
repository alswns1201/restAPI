package com.gugucoding.restful.repository.search;

import com.gugucoding.restful.dto.TodoDTO;
import com.gugucoding.restful.entity.QTodoEntity;
import com.gugucoding.restful.entity.TodoEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch {

    public TodoSearchImpl(){
        super(TodoEntity.class);
    }

    @Override
    public Page<TodoEntity> search1(Pageable pageable) {

        log.info("search1-------------");

        QTodoEntity todoEntity = QTodoEntity.todoEntity;
        JPQLQuery<TodoEntity> query = from(todoEntity);
        query.where(todoEntity.mno.gt(0L));

        getQuerydsl().applyPagination(pageable,query);
        java.util.List<TodoEntity> entityList = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(entityList,pageable,count);
    }

    @Override
    public Page<TodoDTO> searchDTO(Pageable pageable) {
        QTodoEntity todoEntity = QTodoEntity.todoEntity;

        JPQLQuery<TodoEntity> query = from(todoEntity);

        query.where(todoEntity.mno.gt(0L));
        getQuerydsl().applyPagination(pageable,query);

        JPQLQuery<TodoDTO> dtoQuery =
                query.select(Projections.bean
                        (TodoDTO.class,
                                todoEntity.mno,
                                todoEntity.title,
                                todoEntity.writer,
                                todoEntity.dueDate));
        List<TodoDTO> dtoList = dtoQuery.fetch();

        long count = dtoQuery.fetchCount();

//        return new PageImpl<>(dtoList,pageable,count);
        return PageableExecutionUtils.getPage(dtoList, pageable, dtoQuery::fetchCount);
        /* 대량의 경우
         * PageableExecutionUtils.getPage()를 사용하면 불필요한
         * count 쿼리가 실행되는 케이스를 줄일 수 있음을 알 수 있었습니다.
         * 첫 번째 페이지를 조회하거나 마지막 페이지를 조회할 때만큼은
         * 불필요한 count 쿼리가 실행되지 않겠네요.
         */

    }
}

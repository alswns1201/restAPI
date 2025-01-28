package com.gugucoding.restful.review.repository;


import com.gugucoding.restful.review.dto.ReviewDTO;
import com.gugucoding.restful.review.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    // 리뷰와 상품을 두번 조회해야한다면 지연로딩은 쿼리가 두번 실행되니 @Query를 통해 패치 조인을 해야한다.
    @Query("select r from ReviewEntity r join fetch r.productEntity where r.rno = :rno")
    Optional<ReviewEntity> getWithProduct(@Param("rno") Long rno);


    // Entity로 조회하지만, DTO로 처리하게 한다.
    @Query("SELECT r from ReviewEntity r where r.productEntity.pno = :pno")
    Page<ReviewDTO> getPageReview(@Param("pno") Long pno, Pageable pageable);

}

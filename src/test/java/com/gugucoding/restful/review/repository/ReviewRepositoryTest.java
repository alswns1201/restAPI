package com.gugucoding.restful.review.repository;


import com.gugucoding.restful.product.dto.ProductListDTO;
import com.gugucoding.restful.product.repository.ProductRespository;
import com.gugucoding.restful.product.repository.ProductSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductSearch productSearch;


    @Transactional
    @Test
    void 리뷰등록_지연로딩(){
        Long rno = 1L;
        reviewRepository.findById(rno).ifPresent(reviewEntity -> {
            System.out.println(reviewEntity); // 이때는 리뷰만 조회

            System.out.println(reviewEntity.getProductEntity()); // 상품이 필요시 조회 .

        });

    }

    @Transactional
    @Test
    void 리뷰등록_패치조인(){
        Long rno = 1L;
        reviewRepository.getWithProduct(rno).ifPresent(reviewEntity -> {
            System.out.println(reviewEntity); // 이때는 리뷰만 조회

            System.out.println(reviewEntity.getProductEntity()); // 상품이 필요시 조회 .

        });

    }

    @Transactional
    @Test
    void 상품리뷰조회_패치조인(){
        Long pno = 51L;

        Pageable  pageable = PageRequest.of(0,10, Sort.by("rno"));

        reviewRepository.getPageReview(pno, pageable).getContent()
                .forEach(reviewDTO -> {
                    System.out.println(reviewDTO);
                });

    }

    @Transactional
    @Test
    void Querydsl_리뷰수(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("pno").descending());
        Page<ProductListDTO>  result = productSearch.listWithReviewCount(pageable);

        // 각 상품에 대해서 조회 ( 리뷰수 포함 )
        result.getContent().forEach(productListDTO -> {
            System.out.println(productListDTO);
        });

    }



}

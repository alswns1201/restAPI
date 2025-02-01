package com.gugucoding.restful.review.service;


import com.gugucoding.restful.review.dto.ReviewDTO;
import com.gugucoding.restful.review.dto.ReviewPageRequestDTO;
import com.gugucoding.restful.review.entity.ReviewEntity;
import com.gugucoding.restful.review.exception.ReviewException;
import com.gugucoding.restful.review.exception.ReviewTaskExceptions;
import com.gugucoding.restful.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Transactional
@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewService {

private final ReviewRepository reviewRepository;

public ReviewDTO register(ReviewDTO reviewDTO){
       try {
           ReviewEntity reviewEntity = reviewDTO.toEntity();
           reviewRepository.save(reviewEntity);
           return new ReviewDTO(reviewEntity);
       }catch (DataIntegrityViolationException e){
        throw ReviewException.REVIEW_NOT_PRODUCT.get();
       }catch (Exception e){
        throw ReviewException.REVIEW_NOT_REGISTERED.get();
       }
}

public ReviewDTO read(Long rno){
    ReviewEntity reviewEntity = reviewRepository.findById(rno)
            .orElseThrow(ReviewException.REVIEW_NOT_FOUND::get);
    return new ReviewDTO(reviewEntity);

}

public ReviewDTO modify(ReviewDTO reviewDTO){
       ReviewEntity reviewEntity = reviewRepository.findById(reviewDTO.getRno())
                .orElseThrow(ReviewException.REVIEW_NOT_FOUND::get);
    try {
        reviewEntity.changeReviewText(reviewDTO.getReviewText());
        reviewEntity.changeScore(reviewDTO.getScore());
        return new ReviewDTO(reviewEntity);
    }catch (Exception e){
        throw ReviewException.REVIEW_NOT_MODIFIED.get();
    }
}

public void remove(Long rno){

    ReviewEntity reviewEntity = reviewRepository.findById(rno)
                    .orElseThrow(ReviewException.REVIEW_NOT_FOUND::get);
    try {
        reviewRepository.delete(reviewEntity);
    }catch (Exception e){
        throw ReviewException.REVIEW_NOT_DELETE.get();
    }


}

public Page<ReviewDTO> getList(ReviewPageRequestDTO  pageRequestDTO){

    Long pno = pageRequestDTO.getPno(); // 상품 번호를 기준으로 리뷰 페이징 처리

    Pageable pageable  = pageRequestDTO.getPageable(Sort.by("rno").descending());

    return  reviewRepository.getPageReview(pno,pageable);



}


}

package com.gugucoding.restful.review.dto;


import com.gugucoding.restful.product.entity.ProductEntity;
import com.gugucoding.restful.review.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    private Long rno;

    private String reviewText;

    private String reviewer;

    private int score;

    private Long pno;

    private LocalDateTime reviewDate;

    private  LocalDateTime modifiedDate;

    public ReviewDTO(ReviewEntity reviewEntity){
        this.rno = reviewEntity.getRno();
        this.reviewText =reviewEntity.getReviewText();
        this.score = reviewEntity.getScore();
        this.reviewer = reviewEntity.getReviewer();
        this.pno = reviewEntity.getProductEntity().getPno();
        this.reviewDate = reviewEntity.getReviewDate();
        this.modifiedDate = reviewEntity.getModifiedDate();
    }

    public ReviewEntity toEntity(){
        ProductEntity productEntity  = ProductEntity.builder().pno(pno).build();

        return ReviewEntity.builder().rno(rno)
                .reviewText(reviewText)
                .reviewer(reviewer)
                .score(score)
                .productEntity(productEntity)
                .build();
    }


}

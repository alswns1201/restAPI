package com.gugucoding.restful.review.dto;


import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.query.Page;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewPageRequestDTO {

    @Min(1)
    @Builder.Default
    private int page =1;

    @Min(20)
    @Max(100)
    @Builder.Default
    private int size =20;

    private Long pno;

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size,sort);
    }


}

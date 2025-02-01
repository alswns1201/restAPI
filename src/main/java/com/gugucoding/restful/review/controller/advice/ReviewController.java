package com.gugucoding.restful.review.controller.advice;


import com.gugucoding.restful.review.dto.ReviewDTO;
import com.gugucoding.restful.review.dto.ReviewPageRequestDTO;
import com.gugucoding.restful.review.entity.ReviewEntity;
import com.gugucoding.restful.review.exception.ReviewException;
import com.gugucoding.restful.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<ReviewDTO> register(@RequestBody @Validated ReviewDTO reviewDTO
    , Principal principal){

        if(!principal.getName().equals(reviewDTO.getReviewer())){
            throw ReviewException.REVIEW_NOT_MODIFIED.get();
        }

        return ResponseEntity.ok(reviewService.register(reviewDTO));
    }

    @GetMapping("/{rno}")
    public ResponseEntity<ReviewDTO> read(@PathVariable("rno") Long rno){
        return ResponseEntity.ok(reviewService.read(rno));
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<Map<String,String>> remove(@PathVariable("rno") Long rno, Authentication authentication){

        ReviewDTO reviewDTO = reviewService.read(rno);

        if(!authentication.getName().equals(reviewDTO.getReviewer())){
            throw ReviewException.REVIEW_NOT_MODIFIED.get();
        }
        reviewService.remove(rno);

        return ResponseEntity.ok().body(Map.of("result","success"));
    }

    @PutMapping("/{rno}")
    public ResponseEntity<ReviewDTO> modify(@PathVariable("rno") Long rno
    , @RequestBody ReviewDTO reviewDTO ,Authentication authentication){

        if(!rno.equals(reviewDTO.getRno()) || !authentication.getName().equals(reviewDTO.getReviewer())){
            throw ReviewException.REVIEW_NOT_MODIFIED.get();
        }

        return ResponseEntity.ok(reviewService.modify(reviewDTO));
    }

    public ResponseEntity<?> list(@PathVariable("pno") Long pno , @Validated ReviewPageRequestDTO pageRequestDTO){
        pageRequestDTO.setPno(pno);

        return ResponseEntity.ok().body(reviewService.getList(pageRequestDTO));
    }


}

package com.gugucoding.restful.review.exception;

import lombok.Data;

@Data
public class ReviewTaskExceptions extends RuntimeException {
    private String message;
    private int code;

    public  ReviewTaskExceptions(String message,int  code){
        super(message);
        this.message = message;
        this.code =code;

    }
}

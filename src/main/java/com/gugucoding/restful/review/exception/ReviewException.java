package com.gugucoding.restful.review.exception;

public enum ReviewException {

    REVIEW_NOT_REGISTERED("REVIEW NOT Registered",400),
    REVIEW_NOT_PRODUCT("Product not found for review",400),
    REVIEW_NOT_FOUND("Review not found",400),
    REVIEW_NOT_DELETE("Review not delete",400),
    REVIEW_NOT_MODIFIED("Review not modify",400);

    private final ReviewTaskExceptions reviewTaskExceptions;

    ReviewException(String msg, int code){
        reviewTaskExceptions = new ReviewTaskExceptions(msg,code);
    }

    public ReviewTaskExceptions get(){
        return reviewTaskExceptions;
    }

}

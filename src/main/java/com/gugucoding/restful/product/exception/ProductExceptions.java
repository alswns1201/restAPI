package com.gugucoding.restful.product.exception;

public enum ProductExceptions {

    PRODUCT_NOT_FOUND("Product Not Found",404),
    PRODUCT_NOT_REGISTERED("Product Not Registered",400),
    PRODUCT_NOT_MODIFIED("Product Not Modified",400),
    PRODUCT_NOT_REMOVE("Product Not Removed",400),
    PRODUCT_NOT_FETCHED("Product Not Fetched",400),

    PRODUCT_NO_IMAGE("Product no Image",400),
    PRODUCT_WRITER_ERROR("Product Writer Error",403);


    private ProductTaskException productTaskException;

    ProductExceptions(String message,int code){
        this.productTaskException = new ProductTaskException(message,code);
    }

    public ProductTaskException get(){
        return productTaskException;
    }

}

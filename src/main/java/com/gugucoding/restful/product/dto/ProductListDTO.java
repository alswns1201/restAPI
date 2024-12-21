package com.gugucoding.restful.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Data
@NoArgsConstructor
public class ProductListDTO {

    private Long pno;
    private String pname;
    private int price;
    private String writer;
    private String productImage;



}

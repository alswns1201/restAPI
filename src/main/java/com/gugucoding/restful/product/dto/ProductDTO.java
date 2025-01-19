package com.gugucoding.restful.product.dto;


import com.gugucoding.restful.product.entity.ProductEntity;
import com.gugucoding.restful.product.entity.ProductImage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductDTO {

    private Long pno;
    private String pname;
    private  int price;
    private String content;
    private String writer;

    private List<String> imageList;

    public ProductDTO(ProductEntity productEntity){
        this.pname = productEntity.getPname();
        this.pno = productEntity.getPno();
        this.price = productEntity.getPrice();
        this.content = productEntity.getContent();
        this.writer = productEntity.getWriter();
        this.imageList = productEntity.getImages().stream().map(ProductImage::getFileName)
                .collect(Collectors.toList());

    }

    public ProductEntity toEntity(){
        ProductEntity productEntity = ProductEntity.builder()
                .pno(pno)
                .pname(pname)
                .price(price)
                .content(content)
                .writer(writer)
                .build();
        if(imageList ==null || imageList.isEmpty()){
            return productEntity;
        }
        imageList.forEach(productEntity::addImage);
        return productEntity;
    }

}

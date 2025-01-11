package com.gugucoding.restful.product.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "tbl_products")
@Getter
@ToString(exclude = "images")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;

    private String content;

    private String writer;

    @CreatedDate
    private LocalDateTime joinDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "tbl_product_images",joinColumns = @JoinColumn(name = "pno"))
    @Builder.Default
    @BatchSize(size = 100) // N+1 문제 해결 .(1)
    private SortedSet<ProductImage> images = new TreeSet<>();

    public void addImage(String fileName){
        ProductImage productImage = ProductImage.builder().
                fileName(fileName)
                .idx(images.size())
                .build();
    }

    public void clearImages(){
        images.clear();
    }


    public void changeTitle(String title){
        this.pname  =title;
    }

    public void changePrice(int price){
        this.price = price;
    }

    public void changeContent(String content){
        this.content = content;
    }


}

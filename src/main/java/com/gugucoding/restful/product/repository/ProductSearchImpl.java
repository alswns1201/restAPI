package com.gugucoding.restful.product.repository;

import com.gugucoding.restful.product.dto.ProductDTO;
import com.gugucoding.restful.product.dto.ProductListDTO;
import com.gugucoding.restful.product.entity.ProductEntity;
import com.gugucoding.restful.product.entity.QProductEntity;
import com.gugucoding.restful.product.entity.QProductImage;
import com.gugucoding.restful.review.entity.QReviewEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;


public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl(){
        super(ProductEntity.class);
    }


    @Override
    public Page<ProductListDTO> list(Pageable pageable) {

        QProductEntity productEntity = QProductEntity.productEntity;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<ProductEntity> query = from(productEntity);
        query.leftJoin(productEntity.images,productImage);

        query.where(productImage.idx.eq(0));

        JPQLQuery<ProductListDTO> dtojpqlQuery = query.select(Projections.bean(
                ProductListDTO.class, productEntity.pno,
                productEntity.pname,
                productEntity.price,
                productEntity.writer,
                productImage.fileName.as("productImage")));


        this.getQuerydsl().applyPagination(pageable,dtojpqlQuery);

        List<ProductListDTO> dtoList = dtojpqlQuery.fetch();

        long count = dtojpqlQuery.fetchCount();

        return new PageImpl<>(dtoList,pageable,count);

    }

    /**
     *
     * ProductEntity에 이미지에  @BatchSize(size = 100) 설정 하면 in 으로 쿼리가 바뀐다.
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductDTO> listWithAllImages(Pageable pageable) {

        QProductEntity productEntity = QProductEntity.productEntity;
        JPQLQuery<ProductEntity>  query = from(productEntity);
        this.getQuerydsl().applyPagination(pageable,query);

        List<ProductEntity> entityList = query.fetch();
        long count = query.fetchCount();

        // 이미지 가 있는 만큼 N 조회 가 됨 이를 N+1 문제라고 함.
       /*
        for(ProductEntity entity : entityList){
            System.out.println(entity.getImages());
        }
        */
        List<ProductDTO> dtoList = entityList.stream().map(ProductDTO::new).collect(Collectors.toList());

        return new PageImpl<>(dtoList,pageable,count);


    }

    /**
     * 패치로 left join 진행시 하나의 상품에 몇개의 이미지가 있는지 알수 없기때문에
     * 페이징처리는 적용이 안된다.
     * ProductEntity에 이미지에  @BatchSize(size = 100) 설정 하면 in 으로 쿼리가 바뀐지만,
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductDTO> listFetchAllImages(Pageable pageable) {

        QProductEntity productEntity = QProductEntity.productEntity;
        QProductImage productImage = QProductImage.productImage;
        JPQLQuery<ProductEntity>  query = from(productEntity);

        //fetch로 적용.
        query.leftJoin(productEntity.images,productImage).fetchJoin();

        this.getQuerydsl().applyPagination(pageable,query);

        List<ProductEntity> entityList = query.fetch();
        List<ProductDTO> dtoList = entityList.stream().map(ProductDTO::new).toList();
        long count = query.fetchCount();

        return new PageImpl<>(dtoList,pageable,count);


    }

    @Override
    public Page<ProductListDTO> listWithReviewCount(Pageable pageable) {
        QProductEntity productEntity = QProductEntity.productEntity;
        QReviewEntity reviewEntity = QReviewEntity.reviewEntity;
        QProductImage productImage = QProductImage.productImage;

        // select * from productEntity
        JPQLQuery<ProductEntity> query = from(productEntity);
        // left join productEntity = reviewEntity
        query.leftJoin(reviewEntity).on(reviewEntity.productEntity.eq(productEntity));
        query.leftJoin(productEntity.images,productImage);

        //where // 하나의 상품에 대표 이미지만 조회
        query.where(productImage.idx.eq(0));
        this.getQuerydsl().applyPagination(pageable,query);
        // 상품에 여러 리뷰가 있을수 있으니 상품으로 그룹화 .
        query.groupBy(productEntity);

        JPQLQuery<ProductListDTO> dtojpqlQuery =
                query.select(
                        Projections.bean(ProductListDTO.class,
                                productEntity.pno,
                                productEntity.pname,
                                productEntity.writer,
                                productEntity.price,
                                productImage.fileName.as("productImage"), //  조회시 명칭 as "productImage"
                                reviewEntity.countDistinct().as("reviewCount") // 조회시 명칭 as "reviewCount"
                        )
                );
        this.getQuerydsl().applyPagination(pageable,dtojpqlQuery);

        List<ProductListDTO> dtoList = dtojpqlQuery.fetch();

        long count = dtojpqlQuery.fetchCount();

        return new PageImpl<>(dtoList,pageable,count);


    }

    @Override
    public Page<ProductDTO> listWithAllImagesReviewCount(Pageable pageable) {
        QProductEntity productEntity = QProductEntity.productEntity;
        QReviewEntity reviewEntity = QReviewEntity.reviewEntity;
        QProductImage productImage = QProductImage.productImage;


        JPQLQuery<ProductEntity> query = from(productEntity);
        query.leftJoin(reviewEntity).on(reviewEntity.productEntity.eq(productEntity));

        this.getQuerydsl().applyPagination(pageable,query);
        query.groupBy(productEntity);

        // Tuple 을 이용해서 ProductEntity의 값이 아닌 리뷰수도 같이 조회 .
        JPQLQuery<Tuple> tupleJPQLQuery = query.select(productEntity,reviewEntity.countDistinct());

        // tuple[0] 에는 productEntity가 담기고
        // tupple[1]에는 reviewEntity count 가 담긴다.
        List<Tuple> result  = tupleJPQLQuery.fetch();

        List<ProductDTO> dtoList = result.stream().map(tuple -> {
            ProductEntity product = tuple.get(0,ProductEntity.class);
            long count = tuple.get(1,Long.class);

            ProductDTO productDTO = new ProductDTO(product);

            productDTO.setReviewCount(count);
            return productDTO;
        }).toList();
        return new PageImpl<>(dtoList,pageable,tupleJPQLQuery.fetchCount());
    }
}

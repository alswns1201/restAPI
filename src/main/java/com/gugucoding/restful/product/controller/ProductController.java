package com.gugucoding.restful.product.controller;

import com.gugucoding.restful.product.dto.PageRequestDTO;
import com.gugucoding.restful.product.dto.ProductDTO;
import com.gugucoding.restful.product.dto.ProductListDTO;
import com.gugucoding.restful.product.exception.ProductExceptions;
import com.gugucoding.restful.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/lists")
    public ResponseEntity<Page<ProductListDTO>> list(@Validated PageRequestDTO pageRequestDTO,
                                                     Principal principal){
        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> register(@RequestBody @Validated ProductDTO productDTO, Principal principal){
        log.info("resgister-----------------");
        log.info(productDTO);
        if(productDTO.getImageList() == null || productDTO.getImageList().isEmpty()){
            throw ProductExceptions.PRODUCT_NO_IMAGE.get();
        }
        if(!principal.getName().equals(productDTO.getWriter())){
            throw ProductExceptions.PRODUCT_WRITER_ERROR.get();
        }
        return ResponseEntity.ok(productService.register(productDTO));
    }

    @GetMapping("/{pno}")
    public ResponseEntity<ProductDTO> read(@PathVariable("pno") Long pno){
        log.info("read.................");

        ProductDTO productDTO = productService.read(pno);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/{pno}")
    public ResponseEntity<Map<String ,String>> remove(@PathVariable("pno") Long pno, Authentication authentication)
    {
        log.info("remove.............");

        ProductDTO productDTO = productService.read(pno);
        if(!productDTO.getWriter().equals(authentication.getName())){
            Collection<? extends GrantedAuthority> authorities =
                    authentication.getAuthorities();

            authorities.stream().filter(grantedAuthority
                    ->grantedAuthority.getAuthority().equals("ROLE_ADMIN"))
                    .findAny().orElseThrow(ProductExceptions.PRODUCT_WRITER_ERROR::get);
        }
        productService.remove(pno);
        return ResponseEntity.ok(Map.of("result","success"));
    }

    @PutMapping("/{pno}")
    public ResponseEntity<ProductDTO> modify(@PathVariable("pno") Long pno
            ,@RequestBody @Validated ProductDTO productDTO,Authentication authentication){
        log.info("modify--------------------");
        if(!pno.equals(productDTO.getPno())){
            throw ProductExceptions.PRODUCT_NOT_FOUND.get();
        }

        if(productDTO.getImageList() == null || productDTO.getImageList().isEmpty()){
            throw ProductExceptions.PRODUCT_NO_IMAGE.get();
        }

        if(!authentication.getName().equals(productDTO.getWriter())){
            throw ProductExceptions.PRODUCT_WRITER_ERROR.get();
        }
        return ResponseEntity.ok(productService.modify(productDTO));
    }



}

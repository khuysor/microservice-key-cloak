package com.huysor.saas.api.product.controller;

import com.huysor.saas.api.product.dto.req.ProductReq;
import com.huysor.saas.api.product.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ProductController {
    public final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestBody ProductReq req) {
        return productService.createProduct(req);
    }
    @GetMapping("/product")
    public ResponseEntity <?> listProduct(){
        return productService.listProduct();
    }
}

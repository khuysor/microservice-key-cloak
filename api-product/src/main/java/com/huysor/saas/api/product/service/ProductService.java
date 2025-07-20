package com.huysor.saas.api.product.service;

import com.huysor.saas.api.product.dto.req.ProductReq;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> createProduct(ProductReq req);

    ResponseEntity<?> listProduct();
}

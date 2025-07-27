package com.huysor.saas.api.product.controller;

import com.huysor.saas.api.product.dto.req.ProductReq;
import com.huysor.saas.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    public final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductReq req) {
        return productService.createProduct(req);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listProduct(@RequestParam(defaultValue = "5000",name = "duration") Integer duration) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + duration;

        // CPU-intensive loop
        while (System.currentTimeMillis() < endTime) {
            Math.sqrt(Math.random() * 1000000);
        }

        return ResponseEntity.ok("CPU load generated for " + duration + "ms");
    }
}

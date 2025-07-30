package com.huysor.saas.api.product.controller;

import com.huysor.saas.api.product.dto.req.ProductReq;
import com.huysor.saas.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    public final ProductService productService;
    private final Logger log = LoggerFactory.getLogger(ProductController.class);
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
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
        log.info("Fetching product with ID: {}", id);
        return ResponseEntity.ok("Success");
    }
}

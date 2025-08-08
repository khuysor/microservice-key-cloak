package com.huysor.saas.api.product.controller;

import com.huysor.saas.api.product.dto.req.ProductReq;
import com.huysor.saas.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    public ResponseEntity<?> getProduct(@PathVariable("id") Long id ,@RequestHeader("X-Correlation-ID") String correlationId) throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                long startTime = System.currentTimeMillis();
                long endTime = startTime + id;
                while (System.currentTimeMillis() < endTime) {
                    Math.sqrt(Math.random() * 1000000);
                }
                latch.countDown();
            });
        }
        latch.await();
        executor.shutdown();
        return ResponseEntity.ok("Success : "+ id + " Correlation ID: " + correlationId);
    }
}

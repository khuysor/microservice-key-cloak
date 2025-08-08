package com.huysor.saas.api.product.controller;

import com.huysor.saas.api.product.httpClient.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final UserClient userClient;


    @GetMapping("/category")
    public ResponseEntity<?> testFeignClient(@RequestHeader(value = "X-Correlation-ID") String correlationId ) {
        Map<String,Object> filter = new HashMap<>();
        filter.put("name","");
        log.info("Correlation ID: {}", correlationId);
        var user = userClient.getAllUsers(filter);
        return ResponseEntity.ok(user);
    }
}

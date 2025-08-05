package com.huysor.saas.api.product.controller;

import com.huysor.saas.api.product.httpClient.UserClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class CategoryController {
    private final UserClient userClient;

    public CategoryController(UserClient userClient) {
        this.userClient = userClient;
    }
    @GetMapping("/category")
    public ResponseEntity<?> testFeignClient(){
        Map<String,Object> filter = new HashMap<>();
        filter.put("name","");
        var user = userClient.getAllUsers(filter);
        return ResponseEntity.ok(user);
    }
}

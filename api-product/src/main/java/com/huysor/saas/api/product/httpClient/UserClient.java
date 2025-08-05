package com.huysor.saas.api.product.httpClient;


import com.huysor.saas.common.dto.res.ApiRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "api-keycloak-admin")
public interface UserClient {
    @PostMapping("/users/listAllUsers")
    ApiRes<Map<String,Object>> getAllUsers(@RequestBody Map<String,Object> filter);
}

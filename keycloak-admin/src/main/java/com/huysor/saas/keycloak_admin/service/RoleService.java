package com.huysor.saas.keycloak_admin.service;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<ApiRes<String>> createRole(RoleReq req);
    ResponseEntity<ApiRes<?>> listAllRole(int page, int size);
    ResponseEntity<ApiRes<String>> assignRole(RoleReq req);
}

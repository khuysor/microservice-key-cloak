package com.huysor.saas.keycloak_admin.controller;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import com.huysor.saas.keycloak_admin.service.PermissionService;
import com.huysor.saas.keycloak_admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RoleController {
    @Value("${custom.pageSize}")
    private int pageSize;

    private final RoleService roleService;
    private final PermissionService permissionService;


    @PostMapping("/listAllRole")
    public ResponseEntity<ApiRes<?>> listRole(@RequestBody Map<String, Object> req) {
        int page = (Integer) req.getOrDefault("page", 0);
        int size = (Integer) req.getOrDefault("size", pageSize);
        int offset = page > 0 ? (page - 1) * size : 0;
        return roleService.listAllRole(offset, size);
    }

    @PostMapping("/listAllPermission")
    public ResponseEntity<ApiRes<?>> listClientRole(@RequestBody Map<String, Object> req) {
        int page = (Integer) req.getOrDefault("page", 0);
        int size = (Integer) req.getOrDefault("size", pageSize);
        int offset = page > 0 ? (page - 1) * size : 0;
        return ResponseEntity.ok(permissionService.listAllPermission(offset, size));
    }

    @PostMapping("/assignPermissionRole")
    public ResponseEntity<ApiRes<String>> assignPermissionRole(@RequestBody RoleReq req) {
        return roleService.assignRole(req);
    }

    @PostMapping("/createRole")
    public ResponseEntity<ApiRes<String>> createRole(@RequestBody RoleReq req) {
        return roleService.createRole(req);
    }
}

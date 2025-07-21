package com.huysor.saas.keycloak_admin.controller;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.RoleRes;
import com.huysor.saas.keycloak_admin.service.PermissionService;
import com.huysor.saas.keycloak_admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RoleController {
    @Value("${custom.pageSize}")
    private int pageSize;

    private final RoleService roleService;
    private final PermissionService permissionService;


    @PostMapping("/listAllRole")
    public ResponseEntity<ApiRes<PageRes<List<RoleRes>>>> listRole(@RequestBody Map<String, Object> req) {
        int page = (Integer) req.getOrDefault("page", 0);
        int size = (Integer) req.getOrDefault("size", pageSize);
        PageRes<List<RoleRes>> roleResPage = roleService.listAllRole(Math.max(0, page - 1), size);
        return ResponseEntity.ok(ApiRes.success(roleResPage));
    }

    @PostMapping("/listAllPermission")
    public ResponseEntity<ApiRes<?>> listClientRole(@RequestBody Map<String, Object> req) {
        int page = (Integer) req.getOrDefault("page", 0);
        int size = (Integer) req.getOrDefault("size", pageSize);
        return ResponseEntity.ok(permissionService.listAllPermission(page, size));
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

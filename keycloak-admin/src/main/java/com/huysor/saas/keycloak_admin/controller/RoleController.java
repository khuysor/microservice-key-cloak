package com.huysor.saas.keycloak_admin.controller;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.PermissionFilter;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleFilter;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.PermissionRes;
import com.huysor.saas.keycloak_admin.dto.resp.RoleRes;
import com.huysor.saas.keycloak_admin.service.PermissionService;
import com.huysor.saas.keycloak_admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {


    private final RoleService roleService;
    private final PermissionService permissionService;


    @PostMapping("/listAllRole")
    @ResponseStatus(HttpStatus.OK)
    public ApiRes<PageRes<List<RoleRes>>> listRole(@RequestBody RoleFilter req) {
        PageRes<List<RoleRes>> roleResPage = roleService.listAllRole(req);
        return ApiRes.success(roleResPage);
    }

    @PostMapping("/listAllPermission")
    @ResponseStatus(HttpStatus.OK)
    public ApiRes<PageRes<List<PermissionRes>>>  listPermission(@RequestBody PermissionFilter req) {
        return permissionService.listAllPermission(req);
    }

    @PostMapping("/assignPermissionRole")
    @ResponseStatus(HttpStatus.OK)
    public ApiRes<String> assignPermissionRole(@RequestBody RoleReq req) {
        return roleService.assignRole(req);
    }

    @PostMapping("/saveOrUpdate")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiRes<String> createRole(@RequestBody RoleReq req) {
        return roleService.saveOrUpdate(req);
    }
}

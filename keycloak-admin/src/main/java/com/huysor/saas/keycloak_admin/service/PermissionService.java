package com.huysor.saas.keycloak_admin.service;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.PermissionFilter;
import com.huysor.saas.keycloak_admin.dto.resp.PermissionRes;

import java.util.List;

public interface PermissionService {
    boolean assignRolePermission(String roleName, List<String> permissionId);

    ApiRes<PageRes<List<PermissionRes>>> listAllPermission(PermissionFilter filter);
}

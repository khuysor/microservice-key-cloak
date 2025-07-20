package com.huysor.saas.keycloak_admin.service;

import com.huysor.saas.common.dto.res.ApiRes;

import java.util.List;

public interface PermissionService {
    boolean assignRolePermission(String roleName, List<String> permissionId);

    ApiRes<?> listAllPermission(int offset, int size);
}

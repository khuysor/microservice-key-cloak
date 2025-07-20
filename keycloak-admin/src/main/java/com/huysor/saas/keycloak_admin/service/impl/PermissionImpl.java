package com.huysor.saas.keycloak_admin.service.impl;


import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.keycloak_admin.entity.Permissions;
import com.huysor.saas.keycloak_admin.repository.PermissionRepository;
import com.huysor.saas.keycloak_admin.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionImpl implements PermissionService {
    private final PermissionRepository permissionMapper;


    @Override
    public boolean assignRolePermission(String roleName, List<String> permissionId) {
        return false;
    }

    @Override
    public ApiRes<?> listAllPermission(int offset, int size) {
        PageRequest pageRequest = PageRequest.of(offset, size);
        Page<Permissions> permissionPage = permissionMapper.findAll(pageRequest);
        return ApiRes.success(permissionPage);
    }
}

package com.huysor.saas.keycloak_admin.service.impl;


import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.PermissionFilter;
import com.huysor.saas.keycloak_admin.dto.resp.PermissionRes;
import com.huysor.saas.keycloak_admin.entity.Permissions;
import com.huysor.saas.keycloak_admin.repository.PermissionRepository;
import com.huysor.saas.keycloak_admin.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionImpl implements PermissionService {
    private final PermissionRepository permissionRepository;


    @Override
    public boolean assignRolePermission(String roleName, List<String> permissionId) {
        return false;
    }

    @Override
    public ApiRes<PageRes<List<PermissionRes>>> listAllPermission(PermissionFilter filter) {
        Pageable pageable = filter.toPageable();
        if (filter.getName() == null) {
            Page<Permissions> page = permissionRepository.findAll(pageable);
            return getPageResApiRes(page);
        }
        Page<Permissions> page = permissionRepository.findAllByNameContainingIgnoreCase(pageable, filter.getName());
        return getPageResApiRes(page);
    }

    private static ApiRes<PageRes<List<PermissionRes>>> getPageResApiRes(Page<Permissions> page) {
        List<PermissionRes> permissionRes = page.getContent().stream().map(permissions -> new PermissionRes(permissions.getId(), permissions.getName(), permissions.getDescription())).toList();
        return ApiRes.success(new PageRes<>(page.getTotalPages(), page.getTotalElements(), permissionRes));
    }
}

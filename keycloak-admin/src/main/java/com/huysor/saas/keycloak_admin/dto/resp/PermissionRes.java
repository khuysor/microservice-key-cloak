package com.huysor.saas.keycloak_admin.dto.resp;


import com.huysor.saas.keycloak_admin.entity.Permissions;

public record PermissionRes(Long id, String name, String description) {
    static PermissionRes toRes(Permissions permissions){
        return new PermissionRes(
                permissions.getId(),
                permissions.getName(),
                permissions.getDescription()
        );
    }
}

package com.huysor.saas.keycloak_admin.dto.resp;

import com.huysor.saas.keycloak_admin.entity.Role;

import java.util.List;


public record RoleRes(Long id,
                      String name,
                      String description,
                      List<PermissionRes> permissions) {
    static RoleRes toRes(Role role) {
        return new RoleRes(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPermissions().stream().map(PermissionRes::toRes).toList()
        );
    }
}

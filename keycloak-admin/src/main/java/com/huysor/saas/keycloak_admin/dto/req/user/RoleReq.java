package com.huysor.saas.keycloak_admin.dto.req.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleReq {
    private Long id;
    private String name;
    private String roleDescription;
    private List<Long> permissionsId;

    @Getter
    @Setter
    public static class PermissionReq {
        private String roleId;
        private String roleName;
        private String roleDescription;
        private List<String> permissionId;
    }
}

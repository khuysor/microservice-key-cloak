package com.huysor.saas.keycloak_admin.dto.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleResp {
    private Long id;
    private String keycloakId;
    private String name;
    private String description;
    private List<PermissionResp> permissions;
}

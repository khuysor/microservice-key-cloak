package com.huysor.saas.keycloak_admin.dto.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResp {
    private Long id;
    private String name;
    private String description;
    private String keyCloakId;
}

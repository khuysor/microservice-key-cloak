package com.huysor.saas.keycloak_admin.dto.resp;

import java.util.List;

public record RoleRes(Long id,
                      String name,
                      String description,
                      List<PermissionRes> permissions) {

}

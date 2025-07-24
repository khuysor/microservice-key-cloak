package com.huysor.saas.keycloak_admin.dto.req.user;

import java.util.List;

public record RoleReq(Long id, String name, String roleDescription, List<Long> permissionsId) {
}

package com.huysor.saas.keycloak_admin.dto.req.user;

import java.util.Set;

public record RoleReq(Long id, String name, String roleDescription, Set<Long> permissionsId) {
}

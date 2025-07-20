package com.huysor.saas.keycloak_admin.keyCloak;

import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleKeyCloakService {
    List<RoleRepresentation> listAllRealmRole();

    List<RoleRepresentation> listAllClientRole();

    Optional<RoleRepresentation> findRoleByName(String roleName);

    RoleRepresentation createKeyCloakRole(RoleReq req);

    Boolean assignRolePermission(RoleReq req);

    Set<String> findUserRoleName(String userId);

    Set<String> findClientRoleNameByRoleKeyCloakId(String roleId);
}

package com.huysor.saas.keycloak_admin.keyCloak;

import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import com.huysor.saas.keycloak_admin.entity.Permissions;
import com.huysor.saas.keycloak_admin.entity.Role;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleKeyCloakService {
    List<RoleRepresentation> listAllRealmRole();

    List<RoleRepresentation> listAllClientRole();

    Optional<RoleRepresentation> findRoleByName(String roleName);

    void saverOrUpdateRole(RoleReq req, Set<Permissions> permissions);

    Set<String> findUserRoleName(String userId);

    Set<String> findClientRoleNameByRoleName(String roleId);
}

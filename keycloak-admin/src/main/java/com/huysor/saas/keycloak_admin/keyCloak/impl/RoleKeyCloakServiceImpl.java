package com.huysor.saas.keycloak_admin.keyCloak.impl;

import com.huysor.saas.keycloak_admin.config.CustomProperties;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import com.huysor.saas.keycloak_admin.entity.Permissions;
import com.huysor.saas.keycloak_admin.entityMapper.RoleMapping;
import com.huysor.saas.keycloak_admin.keyCloak.RoleKeyCloakService;
import com.huysor.saas.keycloak_admin.repository.PermissionRepository;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleKeyCloakServiceImpl implements RoleKeyCloakService {
    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;
    private final RoleMapping roleMapping;
    private final CustomProperties customProperties;
    private final PermissionRepository permissionRepository;


    @Override
    public List<RoleRepresentation> listAllRealmRole() {
        List<RoleRepresentation> roles = keycloak.realm(realm).roles().list();
        return roles.stream().filter(role -> !role.getName().startsWith("default"))
                .filter(role -> !role.getName().startsWith("uma"))
                .filter(role -> !role.getName().startsWith("offline"))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleRepresentation> listAllClientRole() {
        List<ClientRepresentation> clients = keycloak.realm(realm).clients().findByClientId("backend-keycloak");
        if (!clients.isEmpty()) {
            String clientUuid = clients.get(0).getId();
            List<RoleRepresentation> roles = keycloak.realm(realm).clients().get(clientUuid).roles().list();
        }
        return customProperties.getClients().stream()
                .map(clientId -> {
                    try {
                        return keycloak.realm(realm).clients().findByClientId(clientId).getFirst();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .flatMap(client -> {
                    try {
                        List<RoleRepresentation> roles = keycloak.realm(realm).clients().get(client.getId()).roles().list();
                        return roles.stream();
                    } catch (Exception e) {
                        return java.util.stream.Stream.empty();
                    }
                })
                .collect(Collectors.toList());

    }

    @Override
    public Optional<RoleRepresentation> findRoleByName(String roleName) {
        try {
            return Optional.of(keycloak.realm(realm).roles().get(roleName).toRepresentation());
        } catch (Exception e) {
            log.error("can't find role : {}", e.getMessage());
            return Optional.empty();
        }


    }

    @Override
    public RoleRepresentation createKeyCloakRole(RoleReq req) {
        try {
            RoleRepresentation role = roleMapping.toRoleRepresentation(req);
            keycloak.realm(realm).roles().create(role);
            this.assignRolePermission(req);
            role = keycloak.realm(realm).roles().get(req.getName()).toRepresentation();
            return role;
        } catch (Exception e) {
            log.error("RoleKeyCloak : {}", e.getMessage());
            throw new BadRequestException("Can't create role");
        }
    }

    @Override
    public Boolean assignRolePermission(RoleReq req) {
        try {
            String roleName = req.getName();
            List<RoleRepresentation> currentComposites = keycloak.realm(realm)
                    .roles()
                    .get(roleName).getRoleComposites().stream().toList();
            if (!currentComposites.isEmpty()) {
                // Remove all composite roles
                keycloak.realm(realm)
                        .roles()
                        .get(roleName)
                        .deleteComposites(currentComposites);
            }
            List<Permissions> permission = permissionRepository.findAllById(req.getPermissionsId());
            List<RoleRepresentation> permissions = permission.stream().map(
                    p -> keycloak.realm(realm).clients().get(p.getClientId()).roles().get(p.getName()).toRepresentation()
            ).collect(Collectors.toList());
            keycloak.realm(realm).roles().get(req.getName()).addComposites(permissions);
            return true;
        } catch (Exception e) {
            log.error("Error assignRolePermission: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Set<String> findUserRoleName(String userId) {
        try {
            return keycloak.realm(realm).users().get(userId).roles().realmLevel()
                    .listAll().stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("Error while fetching user role ids from Keycloak: {}", e.getMessage());
            return Set.of();
        }
    }

    @Override
    public Set<String> findClientRoleNameByRoleName(String roleId) {
        try {
            return keycloak.realm(realm).roles().get(roleId).getRoleComposites().stream().map(RoleRepresentation::getName).collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("Error while fetching client role names from Keycloak: {}", e.getMessage());
            return Set.of();
        }
    }

}

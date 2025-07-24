package com.huysor.saas.keycloak_admin.keyCloak.impl;

import com.huysor.saas.keycloak_admin.config.CustomProperties;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import com.huysor.saas.keycloak_admin.entity.Permissions;
import com.huysor.saas.keycloak_admin.entityMapper.RoleMapping;
import com.huysor.saas.keycloak_admin.keyCloak.RoleKeyCloakService;
import com.huysor.saas.keycloak_admin.repository.PermissionRepository;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
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
        return roles.stream().filter(role -> !role.getName().startsWith("default")).filter(role -> !role.getName().startsWith("uma")).filter(role -> !role.getName().startsWith("offline")).collect(Collectors.toList());
    }

    @Override
    public List<RoleRepresentation> listAllClientRole() {
        List<ClientRepresentation> clients = keycloak.realm(realm).clients().findByClientId("backend-keycloak");
        if (!clients.isEmpty()) {
            String clientUuid = clients.get(0).getId();
            List<RoleRepresentation> roles = keycloak.realm(realm).clients().get(clientUuid).roles().list();
        }
        return customProperties.getClients().stream().map(clientId -> {
            try {
                return keycloak.realm(realm).clients().findByClientId(clientId).getFirst();
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull).flatMap(client -> {
            try {
                List<RoleRepresentation> roles = keycloak.realm(realm).clients().get(client.getId()).roles().list();
                return roles.stream();
            } catch (Exception e) {
                return java.util.stream.Stream.empty();
            }
        }).collect(Collectors.toList());

    }

    @Override
    public Optional<RoleRepresentation> findRoleByName(String roleName) {
        try {
            return Optional.ofNullable(keycloak.realm(realm).roles().get(roleName).toRepresentation());
        } catch (Exception e) {
            log.error("can't find role : {}", e.getMessage());
            return Optional.empty();
        }


    }

    @Override
    public void saverOrUpdateRole(RoleReq req, Set<Permissions> permission) {
        try {
            RoleResource roleResource = this.roleResource(req.name());
            List<RoleRepresentation> clientRole = roleResource.getRoleComposites().stream().toList();
            if (!clientRole.isEmpty()) {
                roleResource.deleteComposites(clientRole);
            }
            List<RoleRepresentation> listClientRole = getRoleRepresentations(permission);
            roleResource.addComposites(listClientRole);
        } catch (NotFoundException notFoundException) {
            RoleRepresentation role = roleMapping.toRoleRepresentation(req);
            List<RoleRepresentation> listClientRole = getRoleRepresentations(permission);
            keycloak.realm(realm).roles().create(role);
            this.roleResource(role.getName()).addComposites(listClientRole);

        } catch (Exception e) {
            log.error("RoleKeyCloak : {}", e.getMessage());
            throw new BadRequestException("Can't create role");
        }
    }

    private List<RoleRepresentation> getRoleRepresentations(Set<Permissions> permission) {
        Map<String, String> permissions = permission.stream().collect(Collectors.toMap(Permissions::getName, Permissions::getName));
        Set<String> clientId = permission.stream().map(Permissions::getClientId).collect(Collectors.toSet());
        List<RoleRepresentation> listClientRole = clientId.stream().map(id -> keycloak.realm(realm).clients().get(id).roles().list()).flatMap(List::stream).toList();
        listClientRole = listClientRole.stream().filter(client -> permissions.containsKey(client.getName())).toList();
        return listClientRole;
    }

    RoleResource roleResource(String name) {
        return keycloak.realm(this.realm).roles().get(name);
    }

    @Override
    public Set<String> findUserRoleName(String userId) {
        try {
            return keycloak.realm(realm).users().get(userId).roles().realmLevel().listAll().stream().map(RoleRepresentation::getName).collect(Collectors.toSet());
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

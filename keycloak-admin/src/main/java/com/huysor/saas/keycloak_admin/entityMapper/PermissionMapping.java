package com.huysor.saas.keycloak_admin.entityMapper;

import com.huysor.saas.keycloak_admin.entity.Permissions;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PermissionMapping {
    public Permissions toPermission(RoleRepresentation roleRepresentation) {
        return Permissions.builder()
                .name(roleRepresentation.getName())
                .description(roleRepresentation.getDescription())
                .clientId(roleRepresentation.getContainerId())
                .createdAt(LocalDateTime.now())
                .createdBy("Backend-Service")
                .build();
    }
}

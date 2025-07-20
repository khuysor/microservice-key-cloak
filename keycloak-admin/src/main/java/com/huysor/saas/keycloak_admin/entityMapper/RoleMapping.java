package com.huysor.saas.keycloak_admin.entityMapper;

import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import com.huysor.saas.keycloak_admin.entity.Role;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RoleMapping {
    public RoleRepresentation toRoleRepresentation(RoleReq req) {
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(req.getName());
        roleRepresentation.setDescription(req.getRoleDescription());
        roleRepresentation.setComposite(true);
        return roleRepresentation;
    }

    public Role toRole(RoleRepresentation roleRepresentation) {
        return Role.builder()
                .name(roleRepresentation.getName())
                .description(roleRepresentation.getDescription())
                .keyCloakId(roleRepresentation.getId())
                .createdBy("Backend-Service")
                .createdAt(LocalDateTime.now())
                .build();
    }

}

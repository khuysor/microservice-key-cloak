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
        roleRepresentation.setName(req.name());
        roleRepresentation.setDescription(req.roleDescription());
        roleRepresentation.setComposite(true);
        return roleRepresentation;
    }

    public Role toRole(RoleRepresentation roleRepresentation) {
        return Role.builder()
                .name(roleRepresentation.getName())
                .description(roleRepresentation.getDescription())
                .createdBy("Backend-Service")
                .createdAt(LocalDateTime.now())
                .build();
    }
    public Role toRole(RoleReq req){
        return Role.builder().name(req.name()).description(req.roleDescription()).build();
    }


}

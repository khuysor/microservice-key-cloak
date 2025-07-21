package com.huysor.saas.keycloak_admin.service.impl;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.PermissionRes;
import com.huysor.saas.keycloak_admin.dto.resp.RoleRes;
import com.huysor.saas.keycloak_admin.entity.Permissions;
import com.huysor.saas.keycloak_admin.entity.Role;
import com.huysor.saas.keycloak_admin.entityMapper.RoleMapping;
import com.huysor.saas.keycloak_admin.keyCloak.RoleKeyCloakService;
import com.huysor.saas.keycloak_admin.repository.PermissionRepository;
import com.huysor.saas.keycloak_admin.repository.RoleRepository;
import com.huysor.saas.keycloak_admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleKeyCloakService roleKeyCloakService;
    private final RoleMapping roleMapping;


    @Override
    public ResponseEntity<ApiRes<String>> createRole(RoleReq req) {
        Optional<RoleRepresentation> roleRepresentation = roleKeyCloakService.findRoleByName(req.getName());
        Optional<Role> localRole = roleRepository.findRoleByName(req.getName());
        if (roleRepresentation.isPresent() && localRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiRes.error("Role already exists"));
        }
        if (roleRepresentation.isEmpty()) {
            roleKeyCloakService.createKeyCloakRole(req);
        }
        if (localRole.isEmpty()) {
            Role role = roleMapping.toRole(roleRepresentation.get());
            if (!req.getPermissionsId().isEmpty()) {
                Set<Permissions> permissions = permissionRepository.findAllByIdIn(new HashSet<>(req.getPermissionsId()));
                role.setPermissions(permissions);
            }
            roleRepository.save(role);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success("Role created successfully"));

    }

    @Override
    public PageRes<List<RoleRes>> listAllRole(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdAt"));
        Page<Role> pages = roleRepository.findAll(pageRequest);
        if (pages.isEmpty()) {
            log.error("No roles found in the database");
            return new PageRes<>(0, 0L, List.of());
        }
        List<RoleRes> roleRes = pages.getContent().stream().map(role -> {
            List<PermissionRes> permissionRes = role.getPermissions().stream().map(
                    permission -> new PermissionRes(
                            permission.getId(),
                            permission.getName(),
                            permission.getDescription(),
                            permission.getClientId()
                    )
            ).toList();
            return new RoleRes(
                    role.getId(),
                    role.getName(),
                    role.getDescription(),
                    permissionRes
            );
        }).toList();
        return new PageRes<>(pages.getTotalPages(), pages.getTotalElements(), roleRes);
    }

    @Override
    public ResponseEntity<ApiRes<String>> assignRole(RoleReq req) {
        Optional<Role> localRole = roleRepository.findById(req.getId());
        if (localRole.isEmpty()) {
            log.error("Can't find role in db with id {}", req.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiRes.error("Role does not existing"));
        }
        Optional<RoleRepresentation> roleRepresentation = roleKeyCloakService.findRoleByName(req.getName());
        if (roleRepresentation.isEmpty()) {
            log.error("Can't find role in key cloak with name :{} ", req.getName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiRes.error("Role does not existing"));
        }
        req.setName(roleRepresentation.get().getName());
        boolean assignKeyCloakRole = roleKeyCloakService.assignRolePermission(req);
        if (assignKeyCloakRole) {
            Set<Permissions> permissions = permissionRepository.findAllByIdIn(new HashSet<>(req.getPermissionsId()));
            localRole.get().setPermissions(permissions);
            roleRepository.save(localRole.get());
            return ResponseEntity.ok(ApiRes.created());
        }
        return ResponseEntity.ok(ApiRes.error("Failed to assign role in Keycloak"));
    }
}

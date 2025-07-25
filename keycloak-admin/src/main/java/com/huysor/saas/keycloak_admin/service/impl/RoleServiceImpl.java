package com.huysor.saas.keycloak_admin.service.impl;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleFilter;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @Transactional
    @Override
    public ResponseEntity<ApiRes<String>> saveOrUpdate(RoleReq req) {
        Optional<Role> existingRole = roleRepository.findRoleByName(req.name());
        Set<Permissions> permissions = permissionRepository.findAllByIdIn(new HashSet<>(req.permissionsId()));
        Role role = existingRole.orElseGet(() -> roleMapping.toRole(req));
        role.setPermissions(permissions);
        roleRepository.save(role);
        roleKeyCloakService.saverOrUpdateRole(req, permissions);
        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.created());

    }

    @Override
    public PageRes<List<RoleRes>> listAllRole(RoleFilter req) {
        Pageable pageable = req.toPageable();
        if (req.getName() != null) {
            Page<Role> pages = roleRepository.findAllByNameContainingIgnoreCase(req.getName(), pageable);
            List<RoleRes> res = getRoleResponse(pages);
            return new PageRes<>(pages.getTotalPages(), pages.getTotalElements(), res);
        }
        Page<Role> pages = roleRepository.findAll(pageable);
        if (pages.isEmpty()) {
            log.error("No roles found in the database");
            return new PageRes<>(0, 0L, List.of());
        }
        List<RoleRes> res = getRoleResponse(pages);
        return new PageRes<>(pages.getTotalPages(), pages.getTotalElements(), res);

    }

    private static List<RoleRes> getRoleResponse(Page<Role> pages) {
        return pages.getContent().stream().map(role -> {
            List<PermissionRes> permissionRes = role.getPermissions().stream().map(permission -> new PermissionRes(permission.getId(), permission.getName(), permission.getDescription())).toList();
            return new RoleRes(role.getId(), role.getName(), role.getDescription(), permissionRes);
        }).toList();
    }

    @Override
    public ResponseEntity<ApiRes<String>> assignRole(RoleReq req) {
        return ResponseEntity.ok(ApiRes.error("Failed to assign role in Keycloak"));
    }
}

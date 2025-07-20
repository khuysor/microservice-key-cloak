package com.huysor.saas.keycloak_admin.service.impl;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.keycloak_admin.dto.req.user.UserReq;
import com.huysor.saas.keycloak_admin.dto.req.user.UserRoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.UserResp;
import com.huysor.saas.keycloak_admin.entity.Group;
import com.huysor.saas.keycloak_admin.entity.Role;
import com.huysor.saas.keycloak_admin.entity.User;
import com.huysor.saas.keycloak_admin.entityMapper.UserMapping;
import com.huysor.saas.keycloak_admin.keyCloak.GroupKeyCloakService;
import com.huysor.saas.keycloak_admin.keyCloak.RoleKeyCloakService;
import com.huysor.saas.keycloak_admin.keyCloak.UserKeyCloakService;
import com.huysor.saas.keycloak_admin.repository.GroupRepository;
import com.huysor.saas.keycloak_admin.repository.RoleRepository;
import com.huysor.saas.keycloak_admin.repository.UserRepository;
import com.huysor.saas.keycloak_admin.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final RoleKeyCloakService roleKeyCloakService;
    private final UserMapping userMapping;
    private final UserKeyCloakService userKeyCloakService;
    private final GroupKeyCloakService groupKeyCloakService;


    @Override
    public ResponseEntity<ApiRes<Page<UserResp>>> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<User> userPage = userRepository.findAll(pageRequest);
        if (userPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiRes.error("No users found"));
        }
        Page<UserResp> userRespPage = userPage.map(userMapping::toUserResp);
        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(userRespPage));
    }

    @Transactional
    public ResponseEntity<ApiRes<String>> saveOrUpdateUser(UserReq request) {
        UserRepresentation userRepresentation = userMapping.toUserRepresentation(request);
        Set<Role> roles = roleRepository.findRoleByIdIn(request.roleIds());
        Set<Group> groups = groupRepository.findGroupByIdIn(request.groupIds());
        List<String> roleName = roles.stream().map(Role::getName).toList();
        List<String> groupName = groups.stream().map(Group::getName).toList();
        userRepresentation.setRealmRoles(roleName);
        userRepresentation.setGroups(groupName);
        boolean isSaveSuccess = userKeyCloakService.saveOrUpdateUserKeyCloak(userRepresentation);
        if (!isSaveSuccess) {
            log.error("Failed to save or update user in Keycloak");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiRes.error("Failed to save or update user"));
        }
        Optional<User> userExisting = userRepository.findByUsername(request.username());
        User user;
        if (userExisting.isPresent()) {
            user = userExisting.get();
            userMapping.updateUserFromReq(user, request);
        } else {
            user = userMapping.toUser(request);
        }
        user.setGroups(groups);
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.status(201).body(ApiRes.success("Successfully created"));

    }


    @Override
    public ResponseEntity<ApiRes<UserResp>> getUserDetailById(Long id, Integer languageId) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id: {}", id);
            return new NotFoundException("User not found");
        });
        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success(userMapping.toUserResp(user)));
    }

    @Override
    public ResponseEntity<ApiRes<String>> assignUserRole(UserRoleReq userRoleReq) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiRes.success("Successfully assigned role to user"));
    }
}

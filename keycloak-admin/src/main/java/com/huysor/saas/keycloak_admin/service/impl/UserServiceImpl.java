package com.huysor.saas.keycloak_admin.service.impl;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.UserDelReq;
import com.huysor.saas.keycloak_admin.dto.req.user.UserFilter;
import com.huysor.saas.keycloak_admin.dto.req.user.UserReq;
import com.huysor.saas.keycloak_admin.dto.req.user.UserRoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.UserRes;
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
import com.huysor.saas.keycloak_admin.repository.specifiction.UserSpecification;
import com.huysor.saas.keycloak_admin.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    @ResponseStatus(HttpStatus.OK)
    public PageRes<List<UserRes>> getAllUsers(UserFilter filter) {
        Pageable pageable = filter.toPageable();
        Page<User> userPage = userRepository.findAll(UserSpecification.filterUser(filter), pageable);
        if (userPage.getContent().isEmpty()) {
            return new PageRes<>(0,0L,List.of());
        }
        List<UserRes> users = userPage.getContent().stream().map(UserRes::toUserRes).toList();
        return new PageRes<>(userPage.getTotalPages(), userPage.getTotalElements(), users);
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ApiRes<String> saveOrUpdateUser(UserReq request) {
        UserRepresentation userRepresentation = userMapping.toUserRepresentation(request);
        Optional<User> userExisting = userRepository.findByUsername(request.username());
        User user;
        if (userExisting.isPresent()) {
            user = userExisting.get();
            userMapping.updateUserFromReq(user, request);
        } else {
            user = userMapping.toUser(request);
        }
        if (request.roleIds() != null && !request.roleIds().isEmpty()) {
            Set<Role> roles = roleRepository.findRoleByIdIn(request.roleIds());
            List<String> roleName = roles.stream().map(Role::getName).toList();
            userRepresentation.setRealmRoles(roleName);
            user.setRoles(roles);
        }
        if (request.groupIds() != null && !request.groupIds().isEmpty()) {
            Set<Group> groups = groupRepository.findGroupByIdIn(request.groupIds());
            List<String> groupName = groups.stream().map(Group::getName).toList();
            userRepresentation.setGroups(groupName);
            user.setGroups(groups);
        }
        userKeyCloakService.saveOrUpdateUserKeyCloak(userRepresentation);
        userRepository.save(user);
        return ApiRes.success("Successfully created");
    }


    @Override
    public ApiRes<UserRes> getUserDetailById(Long id, Integer languageId) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id: {}", id);
            return new NotFoundException("User not found");
        });
        return ApiRes.success(userMapping.toUserResp(user));
    }

    @Override
    public ApiRes<String> assignUserRole(UserRoleReq userRoleReq) {
        return ApiRes.success("Successfully assigned role to user");
    }

    @Override
    public ApiRes<String> deleteUser(UserDelReq req) {
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new BadRequestException("Can't delete user"));
        userKeyCloakService.deleteUser(user);
        userRepository.delete(user);
        return null;
    }
}

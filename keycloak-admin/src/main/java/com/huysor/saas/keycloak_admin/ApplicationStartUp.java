package com.huysor.saas.keycloak_admin;

import com.huysor.saas.keycloak_admin.entity.Group;
import com.huysor.saas.keycloak_admin.entity.Permissions;
import com.huysor.saas.keycloak_admin.entity.Role;
import com.huysor.saas.keycloak_admin.entity.User;
import com.huysor.saas.keycloak_admin.entityMapper.PermissionMapping;
import com.huysor.saas.keycloak_admin.entityMapper.RoleMapping;
import com.huysor.saas.keycloak_admin.entityMapper.UserMapping;
import com.huysor.saas.keycloak_admin.keyCloak.GroupKeyCloakService;
import com.huysor.saas.keycloak_admin.keyCloak.RoleKeyCloakService;
import com.huysor.saas.keycloak_admin.keyCloak.UserKeyCloakService;
import com.huysor.saas.keycloak_admin.repository.GroupRepository;
import com.huysor.saas.keycloak_admin.repository.PermissionRepository;
import com.huysor.saas.keycloak_admin.repository.RoleRepository;
import com.huysor.saas.keycloak_admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class ApplicationStartUp implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PermissionRepository permissionRepository;


    private final RoleKeyCloakService roleKeyCloakService;
    private final UserKeyCloakService userKeyCloakService;
    private final UserMapping userMapping;
    private final PermissionMapping permissionMapping;
    private final RoleMapping roleMapping;
    private final GroupKeyCloakService groupKeyCloakService;


    @Override
    public void run(String... args) throws Exception {
        List<GroupRepresentation> groups = groupKeyCloakService.listAllGroups();
        List<Group> localGroups = groupRepository.findAll();
        Map<String, Group> groupMap = localGroups.stream()
                .collect(Collectors.toMap(Group::getKeyCloakId, group -> group));
        groups.forEach(group -> {
            Group existingGroup = groupMap.get(group.getId());
            if (existingGroup != null) {
                existingGroup.setName(group.getName());
                existingGroup.setUpdatedAt(LocalDateTime.now());
                existingGroup.setUpdatedBy("Backend-Service");
                groupRepository.save(existingGroup);
            } else {
                Group localGroup = Group.builder()
                        .name(group.getName())
                        .keyCloakId(group.getId())
                        .updatedAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .updatedBy("Backend-Service")
                        .createdBy("Backend-Service")
                        .build();
                groupRepository.save(localGroup);
            }
        });
        // Sync Client Roles as Permissions
        List<RoleRepresentation> clientRoles = roleKeyCloakService.listAllClientRole();
        Map<String, Permissions> permissionMap = permissionRepository.findAll().stream()
                .collect(Collectors.toMap(Permissions::getName, permissions -> permissions));

        clientRoles.forEach(roleRepresentation -> {
            Permissions existingPermission = permissionMap.get(roleRepresentation.getName());
            if (existingPermission != null) {
                existingPermission.setDescription(roleRepresentation.getDescription());
                existingPermission.setClientId(roleRepresentation.getContainerId());
                existingPermission.setUpdatedAt(LocalDateTime.now());
                existingPermission.setUpdatedBy("Backend-Service");
                permissionRepository.save(existingPermission);
            } else {
                Permissions permissions = permissionMapping.toPermission(roleRepresentation);
                permissionRepository.save(permissions);
            }
        });

        // Sync Realm Roles as Local Roles
        List<RoleRepresentation> realmRoles = roleKeyCloakService.listAllRealmRole();
        Map<String, Role> roleMap = roleRepository.findAll().stream()
                .collect(Collectors.toMap(Role::getName, role -> role));

        realmRoles.forEach(roleRepresentation -> {
            Role findExistingRole = roleMap.get(roleRepresentation.getName());
            Set<String> permissionName = roleKeyCloakService.findClientRoleNameByRoleName(roleRepresentation.getName());
            if (findExistingRole != null) {
                findExistingRole.setName(roleRepresentation.getName());
                findExistingRole.setDescription(roleRepresentation.getDescription());
                findExistingRole.setUpdatedAt(LocalDateTime.now());
                findExistingRole.setUpdatedBy("Backend-Service");
                findExistingRole.setPermissions(permissionRepository.findPermissionsByNameIn(permissionName));
                roleRepository.save(findExistingRole);
            } else {
                Role role = roleMapping.toRole(roleRepresentation);
                role.setPermissions(permissionRepository.findPermissionsByNameIn(permissionName));
                roleRepository.save(role);
            }
        });

        List<UserRepresentation> users = userKeyCloakService.listAllUsers();
        List<User> localUsers = userRepository.findAll();
        Map<String, User> userMap = localUsers.stream().collect(Collectors.toMap(User::getUsername, user -> user));
        users.forEach(userRepresentation -> {
            Set<String> groupId = groupKeyCloakService.findUserGroupIds(userRepresentation.getId());
            Set<String> roleNames = roleKeyCloakService.findUserRoleName(userRepresentation.getId());
            User existingUser = userMap.get(userRepresentation.getUsername());
            Set<Group> group = groupId.stream().map(groupMap::get).collect(Collectors.toSet());
            Set<Role> role = roleNames.stream().map(roleMap::get).collect(Collectors.toSet());
            if (existingUser != null) {
                existingUser.setUsername(userRepresentation.getUsername());
                existingUser.setEmail(userRepresentation.getEmail());
                existingUser.setFirstname(userRepresentation.getFirstName());
                existingUser.setFirstname(userRepresentation.getLastName());
                existingUser.setUpdatedAt(LocalDateTime.now());
                existingUser.setUpdatedBy("Backend-Service");
                existingUser.setRoles(role);
                existingUser.setGroups(group);
                userRepository.save(existingUser);
            } else {
                User user = userMapping.toUser(userRepresentation);
                user.setRoles(role);
                user.setGroups(group);
                userRepository.save(user);
            }
        });
        log.info("Sync Data successfully at {}", LocalDateTime.now());
    }


}
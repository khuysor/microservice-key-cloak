package com.huysor.saas.keycloak_admin.keyCloak.impl;

import com.huysor.saas.keycloak_admin.keyCloak.UserKeyCloakService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserKeyCloakServiceImpl implements UserKeyCloakService {
    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public UserRepresentation findUserByUsername(String username) {
        List<UserRepresentation> users = keycloak.realm(realm).users().searchByUsername(username, true);
        return users.isEmpty() ? null : users.getFirst();
    }


    @Override
    public Boolean deleteUser(String userId) {
        try (Response resp = keycloak.realm(realm).users().delete(userId)) {
            if (resp.getStatus() != 204) {
                log.error("Failed to delete user in Keycloak: status={}, reason={}", resp.getStatus(), resp.getStatusInfo());
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("Failed to delete user in Keycloak: {}", e.getMessage());
            return false;
        }
    }

    public UsersResource getUserResource() {
        return keycloak.realm(realm).users();
    }

    @Override
    public Boolean saveOrUpdateUserKeyCloak(UserRepresentation req) {
        try {
            List<UserRepresentation> existingUser = getUserResource().searchByUsername(req.getUsername(), true);
            if (existingUser.isEmpty()) {
                Response response = this.getUserResource().create(req);
                if (response.getStatus() != 201) {
                    log.error("Failed to create user in Keycloak: status={}, reason={}", response.getStatus(), response.getStatusInfo());
                    return false;
                }
                String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
                this.sendEmail(userId);
                return true;
            }
            UserRepresentation userRepresentations = existingUser.getFirst();
            UserResource userResource = getUserResource().get(userRepresentations.getId());
            userResource.update(req);
            return true;
        } catch (Exception e) {
            log.error("Can't save or update user in key cloak: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public void sendEmail(String userId) {
        try {
            UsersResource usersResource = getUserResource();
            UserResource user = usersResource.get(userId);
            user.sendVerifyEmail();
            log.info("Email sent to user with ID: {}", userId);
        } catch (Exception e) {
            log.error("Failed to send email to user with ID  {}", e.getMessage());
        }
    }

    @Override
    public List<UserRepresentation> listAllUsers() {
        try {
            List<UserRepresentation> users = keycloak.realm(realm).users().list();
            return users.stream().filter(u -> !u.getUsername().startsWith("service")).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to get all users in Keycloak: {}", e.getMessage());
            return List.of();
        }
    }



}

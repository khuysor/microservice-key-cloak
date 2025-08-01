package com.huysor.saas.keycloak_admin.keyCloak.impl;

import com.huysor.saas.keycloak_admin.entity.User;
import com.huysor.saas.keycloak_admin.keyCloak.UserKeyCloakService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

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
    public void deleteUser(User user) {
        List<UserRepresentation> userRepresentations = getUserResource().searchByUsername(user.getUsername(), true);
        if (userRepresentations.isEmpty()) {
            log.error("Can't find user info in keycloak");
            throw new EntityNotFoundException("Can't delete user ");
        }

        try (Response response = getUserResource().delete(userRepresentations.getFirst().getId())) {
            if (response.getStatus() != 204) {
                throw new BadRequestException("Failed to delete user, status: " + response.getStatus());
            }
        } catch (Exception e) {
            throw new BadRequestException("Can't delete user");
        }
    }

    public UsersResource getUserResource() {
        return keycloak.realm(realm).users();
    }

    @Override
    public void saveOrUpdateUserKeyCloak(UserRepresentation req) {
        try {
            List<UserRepresentation> existingUser = getUserResource().searchByUsername(req.getUsername(), true);
            if (existingUser.isEmpty()) {
                try (Response response = this.getUserResource().create(req)) {
                    if (response.getStatus() != 201) {
                        log.error("Failed to create user in Keycloak: status={}, reason={}", response.getStatus(), response.getStatusInfo());
                        throw new IllegalStateException("Failed to create user in Keycloak");
                    }
                    String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
                    this.sendEmail(userId);
                }
            }
            UserRepresentation userRepresentations = existingUser.getFirst();
            getUserResource().get(userRepresentations.getId()).update(req);
        } catch (Exception e) {
            log.error("Can't save or update user in key cloak: {}", e.getMessage());
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Keycloak update failed");
        }
    }

    @Override
    public void sendEmail(String userId) {
        try {
            UserResource user = getUserResource().get(userId);
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

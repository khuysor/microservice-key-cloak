package com.huysor.saas.keycloak_admin.keyCloak;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface UserKeyCloakService {
    UserRepresentation findUserByUsername(String username);

    UsersResource getUserResource();

    Boolean saveOrUpdateUserKeyCloak(UserRepresentation userRepresentation);

    Boolean deleteUser(String userId);

    void sendEmail(String userId);

    List<UserRepresentation> listAllUsers();

}

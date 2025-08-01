package com.huysor.saas.keycloak_admin.keyCloak;

import com.huysor.saas.keycloak_admin.entity.User;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface UserKeyCloakService {
    UserRepresentation findUserByUsername(String username);

    UsersResource getUserResource();

    void saveOrUpdateUserKeyCloak(UserRepresentation userRepresentation);

    void deleteUser(User user);

    void sendEmail(String userId);

    List<UserRepresentation> listAllUsers();

}

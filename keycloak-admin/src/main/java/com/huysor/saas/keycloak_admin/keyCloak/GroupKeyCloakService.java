package com.huysor.saas.keycloak_admin.keyCloak;

import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.representations.idm.GroupRepresentation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupKeyCloakService {
    List<GroupRepresentation> listAllGroups();

    Set<String> findUserGroupIds(String userId);

    Optional<GroupRepresentation> getGroupById(String groupId);
}

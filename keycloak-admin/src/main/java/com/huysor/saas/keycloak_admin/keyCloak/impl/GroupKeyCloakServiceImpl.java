package com.huysor.saas.keycloak_admin.keyCloak.impl;

import com.huysor.saas.keycloak_admin.keyCloak.GroupKeyCloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupKeyCloakServiceImpl implements GroupKeyCloakService {
    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    private final String errorMessage = "Error while fetching groups from Keycloak: {}";

    @Override
    public List<GroupRepresentation> listAllGroups() {
        try {
            return keycloak.realm(realm).groups().groups();
        } catch (Exception exception) {
            log.error(errorMessage, exception.getMessage());
            return List.of();
        }
    }

    @Override
    public Set<String> findUserGroupIds(String userId) {
        try {
            return keycloak.realm(realm).users().get(userId).groups().stream().map(GroupRepresentation::getId).collect(Collectors.toSet());
        } catch (Exception exception) {
            log.error(errorMessage, exception.getMessage());
            return Set.of();
        }
    }

    @Override
    public Optional<GroupRepresentation> getGroupById(String groupId) {
        try {
            return Optional.ofNullable(keycloak.realm(realm).groups().group(groupId).toRepresentation());
        } catch (Exception exception) {
            log.error(errorMessage, exception.getMessage());
            return Optional.empty();
        }

    }

}

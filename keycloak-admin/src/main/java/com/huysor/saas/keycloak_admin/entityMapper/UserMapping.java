package com.huysor.saas.keycloak_admin.entityMapper;

import com.huysor.saas.keycloak_admin.dto.req.user.UserReq;
import com.huysor.saas.keycloak_admin.dto.resp.UserResp;
import com.huysor.saas.keycloak_admin.entity.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapping {
    private final PasswordEncoder encoder;

    public User toUser(UserReq req) {
        User user = new User();
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setFirstname(req.firstName());
        user.setLastname(req.lastName());
        user.setEnabled(req.enabled());
        user.setEmailVerified(req.emailVerified());
        user.setPassword(encoder.encode(req.password()));
        return user;
    }

    public UserResp toUserResp(User user) {
        return new UserResp(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isEnabled(),
                user.isEmailVerified(),
                null,
                null
        );
    }

    public void updateUserFromReq(User user, UserReq req) {
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setFirstname(req.firstName());
        user.setLastname(req.lastName());
        user.setEnabled(req.enabled());
        user.setEmailVerified(req.emailVerified());
        if (req.password() != null && !req.password() .isEmpty()) {
            user.setPassword(encoder.encode(req.password() ));
        }
    }

    public UserRepresentation toUserRepresentation(UserReq request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setEnabled(true);
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        credential.setTemporary(false);
        user.setCredentials(List.of(credential));
        return user;
    }

    public User toUser(UserRepresentation userRepresentation) {
        // You probably don’t get passwords from Keycloak
        return User.builder()
                .firstname(userRepresentation.getFirstName() != null ? userRepresentation.getFirstName() : "")
                .lastname(userRepresentation.getLastName() != null ? userRepresentation.getLastName() : "")
                .email(userRepresentation.getEmail() != null ? userRepresentation.getEmail() : "")
                .username(userRepresentation.getUsername() != null ? userRepresentation.getUsername() : "")
                .enabled(userRepresentation.isEnabled())
                .emailVerified(userRepresentation.isEmailVerified())
                .password("") // You probably don’t get passwords from Keycloak
                .createdAt(LocalDateTime.now())
                .createdBy("Backend-Service")
                .build();
    }

    public void toUser(UserRepresentation representation, User user) {
        user.setFirstname(representation.getFirstName());
        user.setLastname(representation.getLastName());
        user.setEmail(representation.getEmail());
        user.setUsername(representation.getUsername());
        user.setEnabled(representation.isEnabled());
        user.setEmailVerified(representation.isEmailVerified());
    }
}

package com.huysor.saas.keycloak_admin.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huysor.saas.keycloak_admin.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserRes(
        @Schema(description = "User ID")
        @JsonProperty("id")
        Long id,

        @Schema(description = "Username")
        @JsonProperty("username")
        String username,
        @Schema(description = "User email address", format = "email")
        @JsonProperty("email")
        String email,
        @Schema(description = "First name")
        @JsonProperty("firstName")
        String firstName,
        @Schema(description = "Last name")
        @JsonProperty("lastName")
        String lastName,
        @Schema(description = "Creation timestamp", type = "string", format = "date-time")
        @JsonProperty("createAt")
        LocalDateTime createAt,
        @Schema(description = "Update timestamp", type = "string", format = "date-time")
        @JsonProperty("updateAt")
        LocalDateTime updateAt,
        @Schema(description = "Is user enabled")
        @JsonProperty("enabled")
        boolean enabled,
        @Schema(description = "Is email verified")
        @JsonProperty("emailVerified")
        boolean emailVerified,
        @Schema(description = "Roles assigned to the user")
        @JsonProperty("roles")
        Set<RoleRes> roles,
        @Schema(description = "Menus accessible to the user")
        @JsonProperty("menus")
        Set<MenuRes> menus
) {
    public static UserRes toUserRes(User user) {
        return new UserRes(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isEnabled(),
                user.isEmailVerified(),
                user.getRoles().stream().map(RoleRes::toRes).collect(Collectors.toSet()),
                Set.of()
                );

    }
}

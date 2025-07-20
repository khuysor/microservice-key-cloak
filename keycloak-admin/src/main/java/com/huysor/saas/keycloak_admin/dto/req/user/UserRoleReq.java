package com.huysor.saas.keycloak_admin.dto.req.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record UserRoleReq(
        @Schema(
                description = "User ID",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long userId,
        @Schema(
                description = "Set of Role IDs to be assigned to the user",
                example = "[1, 2, 3]",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Set<Long> roleIds) {

}

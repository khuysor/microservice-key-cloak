package com.huysor.saas.keycloak_admin.dto.req.user;

import jakarta.validation.constraints.NotBlank;

public record UserDelReq(
        @NotBlank
        Long userId
) {
}

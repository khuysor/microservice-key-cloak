package com.huysor.saas.keycloak_admin.dto.req.user;



import java.util.Set;

public record UserReq(
         String userId,
         String username,
         String email,
         String password,
         String firstName,
         String lastName,
         Boolean enabled,
         Boolean emailVerified,
         Set<Long> roleIds,
         Set<Long> groupIds
) {

}

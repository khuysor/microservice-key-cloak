package com.huysor.saas.keycloak_admin.service;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.keycloak_admin.dto.req.user.UserReq;
import com.huysor.saas.keycloak_admin.dto.req.user.UserRoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.UserResp;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<ApiRes<Page<UserResp>>> getAllUsers(int page, int size);

    ResponseEntity<ApiRes<String>> saveOrUpdateUser(UserReq request);

    ResponseEntity<ApiRes<UserResp>> getUserDetailById(Long id, Integer languageId);

    ResponseEntity<ApiRes<String>> assignUserRole(UserRoleReq userRoleReq);
}

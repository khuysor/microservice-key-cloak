package com.huysor.saas.keycloak_admin.service;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.UserDelReq;
import com.huysor.saas.keycloak_admin.dto.req.user.UserFilter;
import com.huysor.saas.keycloak_admin.dto.req.user.UserReq;
import com.huysor.saas.keycloak_admin.dto.req.user.UserRoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.UserRes;

import java.util.List;

public interface UserService {

    PageRes<List<UserRes>> getAllUsers(UserFilter filter);

    ApiRes<String> saveOrUpdateUser(UserReq request);

    ApiRes<UserRes> getUserDetailById(Long id, Integer languageId);

    ApiRes<String> assignUserRole(UserRoleReq userRoleReq);

    ApiRes<String> deleteUser(UserDelReq req);
}

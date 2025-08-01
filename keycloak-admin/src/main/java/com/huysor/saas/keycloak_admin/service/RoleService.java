package com.huysor.saas.keycloak_admin.service;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.common.dto.res.PageRes;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleFilter;
import com.huysor.saas.keycloak_admin.dto.req.user.RoleReq;
import com.huysor.saas.keycloak_admin.dto.resp.RoleRes;

import java.util.List;

public interface RoleService {
    ApiRes<String> saveOrUpdate(RoleReq req);

    PageRes<List<RoleRes>> listAllRole(RoleFilter req);

    ApiRes<String> assignRole(RoleReq req);
}

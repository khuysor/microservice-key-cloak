package com.huysor.saas.keycloak_admin.service;

import com.huysor.saas.common.dto.res.ApiRes;
import com.huysor.saas.keycloak_admin.dto.req.MenuReq;
import com.huysor.saas.keycloak_admin.dto.resp.MenuDetailResp;
import com.huysor.saas.keycloak_admin.dto.resp.MenuResp;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuService {
    ResponseEntity<ApiRes<MenuResp>> createMenu(MenuReq menuReq);

    ResponseEntity<ApiRes<List<MenuResp>>> listAllMenu();

    ResponseEntity<ApiRes<MenuResp>> updateMenu(MenuReq menuReq);

    ResponseEntity<ApiRes<MenuDetailResp>> findMenuById(Long id);

    List<MenuResp> findMenuByUserId(Long id, Integer languageId);
}

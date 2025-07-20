//package com.huysor.saas.keycloak_admin.controller;
//
//import com.huysor.saas.keycloak_admin.config.UserContextHolder;
//import com.huysor.saas.keycloak_admin.dto.common.ResultResp;
//import com.huysor.saas.keycloak_admin.dto.req.MenuReq;
//import com.huysor.saas.keycloak_admin.dto.resp.MenuDetailResp;
//import com.huysor.saas.keycloak_admin.dto.resp.MenuResp;
//import com.huysor.saas.keycloak_admin.service.MenuService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class MenuController {
//    private final MenuService menuService;
//    private final UserContextHolder userContextHolder;
//
//    @PreAuthorize("hasAuthority('ROLE_view:menu')")
//    @PostMapping("/allMenu")
//    public ResponseEntity<ResultResp<List<MenuResp>>> listAllMenu() {
//        return menuService.listAllMenu();
//    }
//
//    @PreAuthorize("hasAuthority('ROLE_view:menu')")
//    @PostMapping("/allMenu/{id}/detail")
//    public ResponseEntity<ResultResp<MenuDetailResp>> findMenuById(@PathVariable("id") Long id) {
//        return menuService.findMenuById(id);
//    }
//
//    @PreAuthorize("hasAuthority('ROLE_create:menu')")
//    @PostMapping("/createMenu")
//    public ResponseEntity<ResultResp<MenuResp>> createMenu(MenuReq menuReq) {
//        return menuService.createMenu(menuReq);
//    }
//
//    @PreAuthorize("hasAuthority('ROLE_update:menu')")
//    @PostMapping("/updateMenu")
//    public ResponseEntity<ResultResp<MenuResp>> updateMenu(MenuReq menuReq) {
//        return menuService.updateMenu(menuReq);
//    }
//
//}

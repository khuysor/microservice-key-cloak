//package com.huysor.saas.keycloak_admin.service.impl;
//
//import com.huysor.saas.keycloak_admin.dto.common.ResultResp;
//import com.huysor.saas.keycloak_admin.dto.req.MenuReq;
//import com.huysor.saas.keycloak_admin.dto.resp.MenuDetailResp;
//import com.huysor.saas.keycloak_admin.dto.resp.MenuResp;
//import com.huysor.saas.keycloak_admin.entity.Menu;
//import com.huysor.saas.keycloak_admin.entity.MenuLanguage;
//import com.huysor.saas.keycloak_admin.entityMapper.MenuMapping;
//import com.huysor.saas.keycloak_admin.service.MenuService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class MenuServiceImpl implements MenuService {
//    private final MenuMapper menuMapper;
//    private final MenuMapping menuMapping;
//
//    @Override
//    public ResponseEntity<ResultResp<MenuResp>> createMenu(MenuReq menuReq) {
//        Menu menu = menuMapping.toMenu(menuReq);
//        menuMapper.createMenu(menu);
//        menuReq.getLanguage().forEach(lang -> {
//            MenuLanguage menuLang = new MenuLanguage();
//            menuLang.setMenuId(menu.getId());
//            menuLang.setLanguageId(lang.getLanguageId());
//            menuLang.setName(lang.getName());
//            menuMapper.createMenuLanguage(menuLang);
//        });
//        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResp.success(menuMapping.toMenuResp(menu)));
//    }
//
//    @Override
//    public ResponseEntity<ResultResp<List<MenuResp>>> listAllMenu() {
//        return ResponseEntity.status(HttpStatus.OK).body(ResultResp.success(menuMapper.listAllMenu()));
//    }
//
//    @Override
//    public ResponseEntity<ResultResp<MenuResp>> updateMenu(MenuReq menuReq) {
//        Menu menu = menuMapping.toMenu(menuReq);
//        menuMapper.createMenu(menu);
//        menuReq.getLanguage().forEach(lang -> {
//            MenuLanguage menuLang = new MenuLanguage();
//            menuLang.setMenuId(menu.getId());
//            menuLang.setLanguageId(lang.getLanguageId());
//            menuLang.setName(lang.getName());
//            menuMapper.createMenuLanguage(menuLang);
//        });
//        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResp.success(menuMapping.toMenuResp(menu)));
//    }
//
//    @Override
//    public ResponseEntity<ResultResp<MenuDetailResp>> findMenuById(Long id) {
//        return ResponseEntity.status(HttpStatus.OK).body(ResultResp.success(menuMapper.findMenuById(id)));
//    }
//
//    @Override
//    public List<MenuResp> findMenuByUserId(Long id, Integer languageId) {
//        return menuMapper.findMenuByUserId(id, languageId);
//    }
//}

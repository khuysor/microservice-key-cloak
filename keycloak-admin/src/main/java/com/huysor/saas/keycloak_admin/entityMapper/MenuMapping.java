package com.huysor.saas.keycloak_admin.entityMapper;

import com.huysor.saas.keycloak_admin.dto.req.MenuReq;
import com.huysor.saas.keycloak_admin.dto.resp.MenuResp;
import com.huysor.saas.keycloak_admin.entity.Menu;
import org.springframework.stereotype.Service;

@Service
public class MenuMapping {

    public Menu toMenu(MenuReq req){
        Menu menu = new Menu();
        menu.setId(req.getId());
        menu.setPath(req.getPath());
        menu.setIcon(req.getIcon());
        menu.setComponent(req.getComponent());
        return menu;
    }
    public MenuResp toMenuResp(Menu menu) {
        MenuResp resp = new MenuResp();
        resp.setId(menu.getId());
        resp.setPath(menu.getPath());
        resp.setComponent(menu.getComponent());
        return resp;
    }
    public Menu updateMenuFromReq(Menu menu, MenuReq req) {
        menu.setPath(req.getPath());
        menu.setComponent(req.getComponent());
        return menu;
    }
}

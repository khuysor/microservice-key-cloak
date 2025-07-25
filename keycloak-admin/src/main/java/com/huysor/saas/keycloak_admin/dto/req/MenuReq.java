package com.huysor.saas.keycloak_admin.dto.req;

import com.huysor.saas.keycloak_admin.dto.common.MenuLanguageResp;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuReq {
    private Long id;
    private Long parentId;
    private String path;
    private String component;
    private String icon;
    private List<MenuLanguageResp> language;
}

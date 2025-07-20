package com.huysor.saas.keycloak_admin.dto.resp;

import com.huysor.saas.keycloak_admin.dto.common.MenuLanguageResp;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuDetailResp {
    private Long id;
    private String icon;
    private String path;
    private String component;
    private Long parentId;
    private Integer sortOrder;
    private List<MenuLanguageResp> language;
    private List<MenuDetailResp> child;
}

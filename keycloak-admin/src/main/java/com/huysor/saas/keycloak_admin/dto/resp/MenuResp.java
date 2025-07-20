package com.huysor.saas.keycloak_admin.dto.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResp {
    private Long id;
    private String name;
    private Long parentId;
    private String path;
    private String component;
    private String icon;
    private Integer sortOrder;
    private Integer languageId;
    private List<MenuResp> child;
}

package com.huysor.saas.keycloak_admin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class MenuLanguage {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Integer languageId;
    private Long menuId;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;
}

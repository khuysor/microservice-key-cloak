package com.huysor.saas.keycloak_admin.entity;

import com.huysor.saas.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private String icon;
    private String component;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Menu> children;
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuLanguage> languages;
}

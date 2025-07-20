package com.huysor.saas.keycloak_admin.entity;


import com.huysor.saas.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "tb_user")
@ToString(callSuper = true, exclude = {"roles", "groups"})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "email_verified")
    private boolean emailVerified;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_user_role"
            , joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")}
            , inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")}
            , indexes = {
            @Index(name = "idx_user_role_user_id", columnList = "user_id"),
            @Index(name = "idx_user_role_role_id", columnList = "role_id")
    }
    )
    private Set<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_user_group",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
            }
            , inverseJoinColumns = {
            @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    }
            , indexes = {
            @Index(name = "idx_user_group_user_id", columnList = "user_id"),
            @Index(name = "idx_user_group_group_id", columnList = "group_id")
    }
    )
    private Set<Group> groups;
}

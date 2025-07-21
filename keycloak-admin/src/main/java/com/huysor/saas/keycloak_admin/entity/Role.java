package com.huysor.saas.keycloak_admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huysor.saas.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true,exclude = {"users"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tb_role")
@ToString(callSuper = true, exclude = {"users"})
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER )
    @JoinTable(
            name = "tb_role_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "permission_id")
            ,indexes = {
            @Index(name = "indx_role_id", columnList = "role_id"),
            @Index(name = "indx_permission_id", columnList = "permission_id")
    }
    )
    @JsonIgnore
    private Set<Permissions> permissions;
}

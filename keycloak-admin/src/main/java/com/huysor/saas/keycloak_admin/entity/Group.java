package com.huysor.saas.keycloak_admin.entity;

import com.huysor.saas.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"users"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "tb_group")
@ToString(callSuper = true, exclude = {"users"})
public class Group extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Column(name = "key_cloak_id", unique = true, columnDefinition = "is it id of group from keycloak")
    private String keyCloakId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "groups",fetch = FetchType.LAZY)
    private Set<User> users;
}

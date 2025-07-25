package com.huysor.saas.keycloak_admin.entity;

import com.huysor.saas.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

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
    @Comment("ID of group from Keycloak")
    @Column(name = "key_cloak_id", unique = true)
    private String keyCloakId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "groups",fetch = FetchType.LAZY)
    private Set<User> users;
}

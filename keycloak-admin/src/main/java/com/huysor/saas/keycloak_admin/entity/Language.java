package com.huysor.saas.keycloak_admin.entity;

import com.huysor.saas.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Language extends BaseEntity {
    private Long id;
    private String name;
    private String code;
    private String image;
}

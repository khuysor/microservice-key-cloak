package com.huysor.saas.keycloak_admin.dto.req.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huysor.saas.common.utils.PaginationFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFilter extends PaginationFilter {
    @JsonProperty(value = "name")
    private String name;
}

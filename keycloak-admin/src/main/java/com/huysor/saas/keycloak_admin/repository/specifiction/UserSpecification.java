package com.huysor.saas.keycloak_admin.repository.specifiction;

import com.huysor.saas.common.utils.StringUtils;
import com.huysor.saas.keycloak_admin.dto.req.user.UserFilter;
import com.huysor.saas.keycloak_admin.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class UserSpecification {
    public static Specification<User> filterUser(UserFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isValidString(filter.getName())) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("firstname"))
                                , "%" + filter.getName().toLowerCase() + "%"
                        )
                );
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

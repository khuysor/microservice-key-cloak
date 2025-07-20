package com.huysor.saas.keycloak_admin.repository;

import com.huysor.saas.keycloak_admin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String name);
    Set<Role> findRoleByIdIn(Set<Long> ids);
    Set<Role> findRolesByNameIn(Set<String> names);

}

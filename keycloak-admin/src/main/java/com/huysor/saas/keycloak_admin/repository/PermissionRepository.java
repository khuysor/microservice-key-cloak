package com.huysor.saas.keycloak_admin.repository;

import com.huysor.saas.keycloak_admin.entity.Permissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permissions, Long> {
    Optional<Permissions> findPermissionByName(String name);

    Set<Permissions> findAllByIdIn(Set<Long> ids);

    Set<Permissions> findPermissionByIdIn(Set<Long> ids);

    Set<Permissions> findPermissionsByNameIn(Set<String> names);

    Page<Permissions> findAllByNameContainingIgnoreCase(Pageable pageable, String name);
}


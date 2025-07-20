package com.huysor.saas.keycloak_admin.repository;

import com.huysor.saas.keycloak_admin.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group,Long> {

    Set<Group> findGroupByIdIn(Set<Long> longs);


}

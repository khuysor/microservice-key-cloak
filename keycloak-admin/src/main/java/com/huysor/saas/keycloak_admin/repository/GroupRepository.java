package com.huysor.saas.keycloak_admin.repository;

import com.huysor.saas.keycloak_admin.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupRepository extends JpaRepository<Group,Long> {

    Set<Group> findGroupByIdIn(Set<Long> longs);


}

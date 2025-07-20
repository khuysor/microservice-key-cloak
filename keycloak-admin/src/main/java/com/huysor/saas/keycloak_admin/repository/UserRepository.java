package com.huysor.saas.keycloak_admin.repository;

import com.huysor.saas.keycloak_admin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Long getUserIdByUsername(String username);
}

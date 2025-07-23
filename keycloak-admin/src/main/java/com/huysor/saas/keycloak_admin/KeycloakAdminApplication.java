package com.huysor.saas.keycloak_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KeycloakAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakAdminApplication.class, args);
	}

}

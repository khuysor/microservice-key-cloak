package com.huysor.saas.keycloak_admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@ConfigurationProperties(prefix = "custom")
@Getter
@Setter
public class CustomProperties {
    private List<String> clients;
}

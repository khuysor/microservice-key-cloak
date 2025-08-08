package com.huysor.saas.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("API-PRODUCT", r -> r.path("/api/v1/backend/inventory/**").filters(f -> f.stripPrefix(4)).uri("lb://API-PRODUCT"))
                .route("API-KEYCLOAK-ADMIN", r -> r.path("/api/v1/backend/auth/**").filters(f -> f.stripPrefix(4)).uri("lb://API-KEYCLOAK-ADMIN"))
                .build();
    }
}

package com.huysor.saas.api.product.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
@RequiredArgsConstructor
public class OAuthFeignConfig {
    private final OAuth2AuthorizedClientManager authorizedClientManager;
    @Value("${keycloak.client-id}")
    private String  clientRegistrationId;


    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                    .withClientRegistrationId(clientRegistrationId)
                    .principal(clientRegistrationId)
                    .build();

            OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

            if (authorizedClient != null && authorizedClient.getAccessToken() != null) {
                String token = authorizedClient.getAccessToken().getTokenValue();
                requestTemplate.header("Content-Type", "application/json");
                requestTemplate.header("Authorization", "Bearer " + token);
            } else {
                throw new IllegalStateException("Failed to authorize client for OAuth2 token");
            }
        };
    }
}

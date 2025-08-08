package com.huysor.saas.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Configuration
public class SecurityCommon {
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return converter;
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            return List.of();
        }
        List<String> roles = getStrings(resourceAccess);
        String username = jwt.getClaimAsString("sub");
        Collection<GrantedAuthority> resultRole = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))  // Ex: ROLE_client:view
                .collect(Collectors.toList());
        if (username != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, resultRole);
            SecurityContextHolder.getContext().setAuthentication(authentication); // Setting the SecurityContext
        }
        return resultRole;
    }

    private static List<String> getStrings(Map<String, Object> resourceAccess) {
        List<String> roles = new ArrayList<>();
        for (Map.Entry<String, Object> entry : resourceAccess.entrySet()) {
            String clientName = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                Map<?, ?> clientData = (Map<?, ?>) value;
                Object rolesObj = clientData.get("roles");
                if (rolesObj instanceof List) {
                    List<?> rolesList = (List<?>) rolesObj;
                    for (Object role : rolesList) {
                        if (role instanceof String) {
                            roles.add((String) role); // Collecting roles as strings
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Expected 'roles' to be a List, but got: " + rolesObj.getClass().getName());
                }
            }
        }
        return roles;
    }

}

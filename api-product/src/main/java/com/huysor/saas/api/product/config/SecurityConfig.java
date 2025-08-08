package com.huysor.saas.api.product.config;


import com.huysor.saas.common.config.CustomAccessDeniedHandler;
import com.huysor.saas.common.config.CustomAuthEntryPoint;
import com.huysor.saas.common.config.SecurityCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public SecurityCommon securityCommon() {
        return new SecurityCommon();
    }
    @Bean
    public AuthenticationEntryPoint customAuthEntryPoint() {
        return new CustomAuthEntryPoint();
    }
    @Bean
    AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,SecurityCommon securityCommon) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(
                                jwt -> jwt.jwtAuthenticationConverter(securityCommon.jwtAuthenticationConverter())
                        ).authenticationEntryPoint(customAuthEntryPoint())
                )
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthEntryPoint()).accessDeniedHandler(customAccessDeniedHandler()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}

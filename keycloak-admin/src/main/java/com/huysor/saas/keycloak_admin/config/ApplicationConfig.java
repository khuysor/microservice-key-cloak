package com.huysor.saas.keycloak_admin.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@Configuration
public class ApplicationConfig {
    @Bean
    public PasswordEncoder encoder(){
        Map<String,PasswordEncoder> encoders = Map.of(
                "bcrypt", new BCryptPasswordEncoder(),
                "noop", NoOpPasswordEncoder.getInstance(),
                "argon2", new org.springframework.security.crypto.argon2.Argon2PasswordEncoder(
                        16, // saltLength
                        32, // hashLength
                        1,  // parallelism
                        65536, // memory (in KB)
                        3  )
        );
        return new DelegatingPasswordEncoder("bcrypt", encoders);
    }
}

package com.pramod.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // 👈 Disable CSRF (recommended syntax)
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/auth/**").permitAll()
                        .anyExchange().authenticated()
                );

        return http.build();
    }
}

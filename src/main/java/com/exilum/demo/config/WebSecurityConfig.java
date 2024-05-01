package com.exilum.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.List;


import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        // permit all requests to /admin/user-claims/**
                        // TODO: change before deploying
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/api/auth/*").permitAll()
                        // require authentication for the rest
                        .anyRequest().authenticated()
                )
                .csrf().disable()
                // Configure oauth2 resource server
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                        // Use the custom converter (defined under this)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt ->
                // Retrieve the list from the "custom_claims" property of the JWT token
                Optional.ofNullable(jwt.getClaimAsStringList("custom_claims"))
                        .stream()
                        .flatMap(List::stream)
                // then map it to instances of SimpleGrantedAuthority
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );

        return converter;
    }
}

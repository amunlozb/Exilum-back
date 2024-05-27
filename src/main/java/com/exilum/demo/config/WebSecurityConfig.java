package com.exilum.demo.config;

import com.exilum.demo.config.FirebaseSessionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorize -> authorize
                        // Test Endpoints
/*                        .requestMatchers("/api/test/public").permitAll()
                        .requestMatchers("/api/test/authenticated").authenticated()
                        .requestMatchers("/api/test/admin").hasRole("ADMIN")
                        // Auth Endpoints
                        .requestMatchers("/api/auth/**").permitAll()*/
                        // .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(firebaseSessionFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt ->
                Optional.ofNullable(jwt.getClaimAsStringList("custom_claims"))
                        .stream()
                        .flatMap(List::stream)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
        return converter;
    }

    @Bean
    public FirebaseSessionFilter firebaseSessionFilter() {
        return new FirebaseSessionFilter();
    }
}

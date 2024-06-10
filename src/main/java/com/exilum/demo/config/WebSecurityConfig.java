    package com.exilum.demo.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    import java.util.ArrayList;
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
                            .requestMatchers("/api/test/public").permitAll()
                            .requestMatchers("/api/test/authenticated").authenticated()
                            .requestMatchers("/api/test/admin").hasRole("ADMIN")
                            // Auth Endpoints
                            .requestMatchers("/api/auth/**").permitAll()
                            // Web Endpoints
                            .requestMatchers("/api/**").permitAll()
                            .requestMatchers("/api/strategy/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                            // Use the custom converter (defined under this)
                            .jwtAuthenticationConverter(jwtAuthenticationConverter())));

            return http.build();
        }

        public JwtAuthenticationConverter jwtAuthenticationConverter() {
            JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
            converter.setJwtGrantedAuthoritiesConverter(jwt -> {
                List<String> customClaims = jwt.getClaimAsStringList("custom_claims");
                List<GrantedAuthority> authorities = new ArrayList<>();

                if (customClaims != null && customClaims.contains("ADMIN")) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }

                return authorities;
            });
            return converter;
        }
    }

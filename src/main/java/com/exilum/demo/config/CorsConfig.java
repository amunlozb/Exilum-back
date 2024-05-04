package com.exilum.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //TODO: change to deployed front-end url (with allowedOrigins)
        registry
                .addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowCredentials(true);
    }
}

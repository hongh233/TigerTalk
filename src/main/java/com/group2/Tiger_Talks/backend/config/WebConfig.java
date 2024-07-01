package com.group2.Tiger_Talks.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")  // work for all
                        .allowedOrigins("http://localhost:3000", "https://668212412d3c73b6357b6681--incredible-paprenjak-d88718.netlify.app/")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                        .allowedHeaders("Content-Type", "Authorization")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
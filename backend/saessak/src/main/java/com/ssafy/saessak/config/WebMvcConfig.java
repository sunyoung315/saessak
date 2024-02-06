package com.ssafy.saessak.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", 
                "http://i10a706.p.ssafy.io/",
                "https://i10a706.p.ssafy.io/"
                "http://i10a706.p.ssafy.io:5173");
    }
}
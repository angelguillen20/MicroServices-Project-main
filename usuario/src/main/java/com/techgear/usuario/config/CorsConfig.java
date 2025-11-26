package com.techgear.usuario.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // Aplica a todas las rutas de la API
            .allowedOrigins("http://44.220.255.227", // Nueva IP de EC2
                            "http://reactpokeshop.s3-website-us-east-1.amazonaws.com", // Dominio de S3
                            "http://localhost:5173") // O tu puerto de desarrollo local (Vite)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*");
}
}
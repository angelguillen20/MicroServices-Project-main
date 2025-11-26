package com.techgear.catalogo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global para Cross-Origin Resource Sharing (CORS).
 * Esto permite que el frontend (ej. React en localhost:5173) acceda al backend (Spring Boot en localhost:8082).
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Habilita CORS para todos los endpoints (/**)
        registry.addMapping("/**")
                // Permite el origen de tu aplicación React
                .allowedOrigins("http://localhost:5173") 
                // Permite los métodos HTTP comunes (GET, POST, PUT, DELETE, etc.)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // Permite cualquier encabezado en la solicitud
                .allowedHeaders("*")
                // Permite el uso de credenciales (cookies, encabezados de autorización)
                .allowCredentials(true);
    }
}
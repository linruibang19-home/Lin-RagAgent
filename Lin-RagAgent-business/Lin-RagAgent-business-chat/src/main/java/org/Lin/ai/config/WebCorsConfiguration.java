package org.Lin.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Allows the separately deployed web client to call the API.
 */
@Configuration
public class WebCorsConfiguration implements WebMvcConfigurer {

    private final String[] allowedOriginPatterns;

    public WebCorsConfiguration(
        @Value("${app.web.cors.allowed-origin-patterns:http://127.0.0.1:5173,http://localhost:5173}")
        String[] allowedOriginPatterns) {
        this.allowedOriginPatterns = allowedOriginPatterns;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns(allowedOriginPatterns)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .exposedHeaders("*")
            .maxAge(3600);
    }
}

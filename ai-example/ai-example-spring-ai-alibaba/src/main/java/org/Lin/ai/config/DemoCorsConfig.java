package org.Lin.ai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 配置类
 * @author: Lin-RAGAgent
 **/
@Configuration
public class DemoCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/agent/**")
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "OPTIONS")
            .allowedHeaders("*")
            .maxAge(3600);
    }

}

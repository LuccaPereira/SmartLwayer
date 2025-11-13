package smartLegalApi.presentation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração Web da aplicação
 * Configura CORS, interceptors, etc
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configuração de CORS para permitir requisições do frontend
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:3000",    // Frontend em desenvolvimento
                "http://localhost:9000",    // Webpack dev server
                "https://smartlegal.com.br" // Produção
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}


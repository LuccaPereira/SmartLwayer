package smartLegalApi.infrastructure.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Propriedades de configuração da API Gemini
 */
@Component
@ConfigurationProperties(prefix = "gemini")
@Data
public class GeminiProperties {
    
    /**
     * API Key do Google Gemini
     */
    private String apiKey;
    
    /**
     * Modelo a ser utilizado
     */
    private String model = "gemini-pro";
    
    /**
     * Número máximo de tokens na resposta
     */
    private Integer maxTokens = 4096;
    
    /**
     * Temperatura (criatividade) da IA (0.0 a 1.0)
     */
    private Double temperature = 0.7;
    
    /**
     * URL base da API Gemini
     */
    private String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models";
}


package smartLegalApi.infrastructure.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Propriedades de configuração do JWT
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    
    /**
     * Chave secreta para assinatura do token
     */
    private String secret = "SmartLegalApiSecretKey2024MuitoSeguraParaProducao123456789";
    
    /**
     * Tempo de expiração do token em milissegundos (padrão: 24 horas)
     */
    private Long expiration = 86400000L; // 24 horas
    
    /**
     * Tempo de expiração do refresh token em milissegundos (padrão: 7 dias)
     */
    private Long refreshExpiration = 604800000L; // 7 dias
    
    /**
     * Issuer do token
     */
    private String issuer = "SmartLegalApi";
    
    /**
     * Prefixo do token no header Authorization
     */
    private String tokenPrefix = "Bearer ";
    
    /**
     * Nome do header de autorização
     */
    private String headerName = "Authorization";
}


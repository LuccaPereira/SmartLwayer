package smartLegalApi.infrastructure.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serviço para gerenciar tokens de reset de senha
 * 
 * NOTA: Em produção, usar Redis ou banco de dados para armazenar tokens
 * Esta implementação em memória é APENAS para desenvolvimento/testes
 */
@Service
@Slf4j
public class PasswordResetTokenService {
    
    private static final int TOKEN_EXPIRATION_HOURS = 1;
    private static final int TOKEN_LENGTH = 32;
    
    // APENAS PARA DESENVOLVIMENTO - Em produção usar Redis ou DB
    private final Map<String, TokenInfo> tokens = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();
    
    /**
     * Gera um token de reset de senha
     */
    public String generateResetToken(Long userId, String email) {
        // Gera token aleatório seguro
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(tokenBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
        
        // Armazena o token com expira\u00e7\u00e3o
        LocalDateTime expiration = LocalDateTime.now().plusHours(TOKEN_EXPIRATION_HOURS);
        tokens.put(token, new TokenInfo(userId, email, expiration));
        
        log.info("Token de reset gerado para usuário ID: {} (expira em {} hora)", userId, TOKEN_EXPIRATION_HOURS);
        
        // Limpa tokens expirados (cleanup simples)
        cleanupExpiredTokens();
        
        return token;
    }
    
    /**
     * Valida um token e retorna o ID do usuário
     * @return ID do usuário ou null se token inválido
     */
    public Long validateAndGetUserId(String token) {
        TokenInfo tokenInfo = tokens.get(token);
        
        if (tokenInfo == null) {
            log.warn("Token de reset não encontrado");
            return null;
        }
        
        if (tokenInfo.expiration().isBefore(LocalDateTime.now())) {
            log.warn("Token de reset expirado");
            tokens.remove(token);
            return null;
        }
        
        return tokenInfo.userId();
    }
    
    /**
     * Invalida um token (após uso bem-sucedido)
     */
    public void invalidateToken(String token) {
        tokens.remove(token);
        log.info("Token de reset invalidado");
    }
    
    /**
     * Remove tokens expirados da memória
     */
    private void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokens.entrySet().removeIf(entry -> entry.getValue().expiration().isBefore(now));
    }
    
    /**
     * Informações do token
     */
    private record TokenInfo(
        Long userId,
        String email,
        LocalDateTime expiration
    ) {}
}


package smartLegalApi.infrastructure.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import smartLegalApi.infrastructure.security.config.JwtProperties;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Serviço para geração e validação de tokens JWT
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    
    private final JwtProperties jwtProperties;
    
    /**
     * Gera token JWT para o usuário
     */
    public String generateToken(String email, Long userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("role", role);
        claims.put("type", "ACCESS");
        
        return createToken(claims, email, jwtProperties.getExpiration());
    }
    
    /**
     * Gera refresh token
     */
    public String generateRefreshToken(String email, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("type", "REFRESH");
        
        return createToken(claims, email, jwtProperties.getRefreshExpiration());
    }
    
    /**
     * Cria o token JWT
     */
    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
            .claims(claims)
            .subject(subject)
            .issuer(jwtProperties.getIssuer())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getSigningKey())
            .compact();
    }
    
    /**
     * Extrai o email (subject) do token
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    /**
     * Extrai o ID do usuário do token
     */
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }
    
    /**
     * Extrai o role do token
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
    
    /**
     * Extrai a data de expiração do token
     */
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
    
    /**
     * Extrai todos os claims do token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
    
    /**
     * Verifica se o token está expirado
     */
    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (JwtException e) {
            log.error("Erro ao verificar expiração do token: {}", e.getMessage());
            return true;
        }
    }
    
    /**
     * Valida o token
     */
    public boolean validateToken(String token, String email) {
        try {
            String tokenEmail = extractEmail(token);
            return (tokenEmail.equals(email) && !isTokenExpired(token));
        } catch (MalformedJwtException e) {
            log.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token JWT não suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string está vazia: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Erro ao validar token JWT: {}", e.getMessage());
        }
        return false;
    }
    
    /**
     * Valida o token sem verificar o email
     */
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Token inválido: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Remove o prefixo "Bearer " do token
     */
    public String removeBearerPrefix(String token) {
        if (token != null && token.startsWith(jwtProperties.getTokenPrefix())) {
            return token.substring(jwtProperties.getTokenPrefix().length());
        }
        return token;
    }
    
    /**
     * Obtém a chave de assinatura
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}


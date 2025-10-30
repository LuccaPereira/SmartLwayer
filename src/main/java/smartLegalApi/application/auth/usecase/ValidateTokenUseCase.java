package smartLegalApi.application.auth.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import smartLegalApi.infrastructure.security.service.CustomUserDetailsService;
import smartLegalApi.infrastructure.security.service.JwtService;
import smartLegalApi.infrastructure.security.model.UserPrincipal;

/**
 * Caso de uso: Validar token e retornar informações do usuário
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateTokenUseCase {
    
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    
    public ValidationResult executar(String token) {
        log.debug("Validando token");
        
        try {
            if (!jwtService.validateToken(token)) {
                return new ValidationResult(false, null, null, null);
            }
            
            String email = jwtService.extractEmail(token);
            Long userId = jwtService.extractUserId(token);
            String role = jwtService.extractRole(token);
            
            UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(email);
            
            return new ValidationResult(
                true,
                userId,
                email,
                role
            );
            
        } catch (Exception e) {
            log.error("Erro ao validar token: {}", e.getMessage());
            return new ValidationResult(false, null, null, null);
        }
    }
    
    /**
     * Resultado da validação
     */
    public record ValidationResult(
        boolean valid,
        Long userId,
        String email,
        String role
    ) {}
}


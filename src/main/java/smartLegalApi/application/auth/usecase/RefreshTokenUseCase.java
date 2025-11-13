package smartLegalApi.application.auth.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.infrastructure.security.service.CustomUserDetailsService;
import smartLegalApi.infrastructure.security.service.JwtService;
import smartLegalApi.infrastructure.security.model.UserPrincipal;

/**
 * Caso de uso: Renovar token de acesso usando refresh token
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenUseCase {
    
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    
    @Transactional(readOnly = true)
    public RefreshResult executar(String refreshToken) {
        log.info("Tentativa de refresh token");
        
        try {
            // Valida o refresh token
            if (!jwtService.validateToken(refreshToken)) {
                throw new BadCredentialsException("Refresh token inválido ou expirado");
            }
            
            // Extrai dados do token
            String email = jwtService.extractEmail(refreshToken);
            Long userId = jwtService.extractUserId(refreshToken);
            
            // Carrega os dados atualizados do usuário
            UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(email);
            
            // Gera novo access token
            String newAccessToken = jwtService.generateToken(
                userPrincipal.getEmail(),
                userPrincipal.getId(),
                userPrincipal.getRole()
            );
            
            log.info("Token renovado com sucesso para: {}", email);
            
            return new RefreshResult(
                newAccessToken,
                refreshToken,
                userPrincipal.getId(),
                userPrincipal.getEmail()
            );
            
        } catch (Exception e) {
            log.error("Erro ao renovar token: {}", e.getMessage());
            throw new BadCredentialsException("Não foi possível renovar o token");
        }
    }
    
    /**
     * Resultado do refresh
     */
    public record RefreshResult(
        String accessToken,
        String refreshToken,
        Long userId,
        String email
    ) {}
}


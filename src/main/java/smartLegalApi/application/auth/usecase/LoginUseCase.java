package smartLegalApi.application.auth.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.infrastructure.security.model.UserPrincipal;
import smartLegalApi.infrastructure.security.service.JwtService;

/**
 * Caso de uso: Login de usuário
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoginUseCase {
    
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    
    @Transactional(readOnly = true)
    public LoginResult executar(String email, String senha) {
        log.info("Tentativa de login para email: {}", email);
        
        try {
            // Autentica o usuário
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha)
            );
            
            // Obtém os dados do usuário autenticado
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            
            // Gera os tokens
            String accessToken = jwtService.generateToken(
                userPrincipal.getEmail(),
                userPrincipal.getId(),
                userPrincipal.getRole()
            );
            
            String refreshToken = jwtService.generateRefreshToken(
                userPrincipal.getEmail(),
                userPrincipal.getId()
            );
            
            log.info("Login realizado com sucesso para: {}", email);
            
            return new LoginResult(
                accessToken,
                refreshToken,
                userPrincipal.getId(),
                userPrincipal.getEmail(),
                userPrincipal.getNome(),
                userPrincipal.getRole()
            );
            
        } catch (BadCredentialsException e) {
            log.error("Credenciais inválidas para: {}", email);
            throw new BadCredentialsException("Email ou senha incorretos");
        }
    }
    
    /**
     * Resultado do login
     */
    public record LoginResult(
        String accessToken,
        String refreshToken,
        Long userId,
        String email,
        String nome,
        String role
    ) {}
}


package smartLegalApi.infrastructure.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import smartLegalApi.infrastructure.security.config.JwtProperties;
import smartLegalApi.infrastructure.security.service.CustomUserDetailsService;
import smartLegalApi.infrastructure.security.service.JwtService;

import java.io.IOException;

/**
 * Filtro JWT para autenticação de requisições
 * NÃO usa @Component - deve ser configurado manualmente no SecurityConfig
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;
    
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService, JwtProperties jwtProperties) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.jwtProperties = jwtProperties;
    }
    
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        try {
            String authHeader = request.getHeader(jwtProperties.getHeaderName());
            
            // Verifica se o header Authorization existe e tem o formato correto
            if (authHeader == null || !authHeader.startsWith(jwtProperties.getTokenPrefix())) {
                filterChain.doFilter(request, response);
                return;
            }
            
            // Extrai o token removendo o prefixo "Bearer "
            String token = jwtService.removeBearerPrefix(authHeader);
            
            // Extrai o email do token
            String email = jwtService.extractEmail(token);
            
            // Se o email existe e ainda não há autenticação no contexto
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Carrega os detalhes do usuário
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                
                // Valida o token
                if (jwtService.validateToken(token, email)) {
                    
                    // Cria o objeto de autenticação
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    
                    // Define detalhes adicionais da requisição
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Define a autenticação no contexto do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            
        } catch (Exception e) {
            // Log de erro silencioso - JWT inválido ou expirado
        }
        
        filterChain.doFilter(request, response);
    }
}


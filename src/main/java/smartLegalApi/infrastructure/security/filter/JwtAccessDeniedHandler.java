package smartLegalApi.infrastructure.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import smartLegalApi.presentation.exception.dto.ErrorResponse;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Handler para erros de acesso negado
 */
@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        
        log.error("Acesso negado: {}", accessDeniedException.getMessage());
        
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpServletResponse.SC_FORBIDDEN)
            .error("Acesso negado")
            .message("Você não tem permissão para acessar este recurso")
            .path(request.getRequestURI())
            .build();
        
        objectMapper.findAndRegisterModules();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}


package smartLegalApi.infrastructure.audit.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import smartLegalApi.domain.audit.entity.AuditLog;
import smartLegalApi.domain.audit.repository.AuditLogRepository;
import smartLegalApi.infrastructure.audit.annotation.Auditable;
import smartLegalApi.infrastructure.security.model.UserPrincipal;

import java.lang.reflect.Method;

/**
 * Aspecto AOP para auditoria automática
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {
    
    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;
    
    @AfterReturning(
        pointcut = "@annotation(smartLegalApi.infrastructure.audit.annotation.Auditable)",
        returning = "result"
    )
    public void auditarOperacao(JoinPoint joinPoint, Object result) {
        try {
            // Obtém a anotação @Auditable
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Auditable auditable = method.getAnnotation(Auditable.class);
            
            if (auditable == null) return;
            
            // Obtém informações do usuário autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long idUsuario = null;
            String nomeUsuario = "Sistema";
            
            if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                idUsuario = userPrincipal.getId();
                nomeUsuario = userPrincipal.getNome();
            }
            
            // Extrai ID da entidade do resultado (se possível)
            Long idEntidade = extrairIdEntidade(result);
            
            // Monta detalhes
            String detalhes = auditable.descricao().isEmpty() 
                ? auditable.operacao().getDescricao() + " em " + auditable.entidade()
                : auditable.descricao();
            
            // Cria log de auditoria
            AuditLog auditLog = AuditLog.criar(
                idUsuario,
                nomeUsuario,
                auditable.operacao(),
                auditable.entidade(),
                idEntidade,
                detalhes
            );
            
            // Adiciona informações HTTP
            adicionarContextoHttp(auditLog);
            
            // Salva o log
            auditLogRepository.save(auditLog);
            
            log.debug("Auditoria registrada: {} - {} - ID: {}", 
                auditable.operacao(), auditable.entidade(), idEntidade);
            
        } catch (Exception e) {
            // Não deve quebrar a aplicação se auditoria falhar
            log.error("Erro ao registrar auditoria", e);
        }
    }
    
    /**
     * Tenta extrair o ID da entidade do resultado
     */
    private Long extrairIdEntidade(Object result) {
        if (result == null) return null;
        
        try {
            // Tenta encontrar método getId()
            Method getIdMethod = result.getClass().getMethod("getId");
            Object id = getIdMethod.invoke(result);
            
            if (id instanceof Long) {
                return (Long) id;
            } else if (id instanceof Number) {
                return ((Number) id).longValue();
            }
        } catch (Exception e) {
            // Ignora se não conseguir extrair o ID
        }
        
        return null;
    }
    
    /**
     * Adiciona informações do contexto HTTP
     */
    private void adicionarContextoHttp(AuditLog auditLog) {
        try {
            ServletRequestAttributes attributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                
                String ipAddress = obterIpAddress(request);
                String userAgent = request.getHeader("User-Agent");
                
                auditLog.adicionarContextoHttp(ipAddress, userAgent);
            }
        } catch (Exception e) {
            // Ignora se não conseguir obter contexto HTTP
        }
    }
    
    /**
     * Obtém o IP real do cliente (considerando proxies)
     */
    private String obterIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        // Se tiver múltiplos IPs, pega o primeiro
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        
        return ip;
    }
}


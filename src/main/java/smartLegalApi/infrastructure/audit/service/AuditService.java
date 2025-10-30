package smartLegalApi.infrastructure.audit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import smartLegalApi.domain.audit.entity.AuditLog;
import smartLegalApi.domain.audit.repository.AuditLogRepository;
import smartLegalApi.domain.audit.valueobject.TipoOperacao;
import smartLegalApi.infrastructure.security.model.UserPrincipal;

/**
 * Serviço para auditoria manual (quando não usar @Auditable)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {
    
    private final AuditLogRepository auditLogRepository;
    
    /**
     * Registra um log de auditoria manualmente
     */
    public void registrar(
        TipoOperacao operacao,
        String entidade,
        Long idEntidade,
        String detalhes
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long idUsuario = null;
            String nomeUsuario = "Sistema";
            
            if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
                UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                idUsuario = userPrincipal.getId();
                nomeUsuario = userPrincipal.getNome();
            }
            
            AuditLog auditLog = AuditLog.criar(
                idUsuario,
                nomeUsuario,
                operacao,
                entidade,
                idEntidade,
                detalhes
            );
            
            auditLogRepository.save(auditLog);
            
            log.debug("Auditoria manual registrada: {} - {} - ID: {}", operacao, entidade, idEntidade);
            
        } catch (Exception e) {
            log.error("Erro ao registrar auditoria manual", e);
        }
    }
    
    /**
     * Registra login de usuário
     */
    public void registrarLogin(Long idUsuario, String nomeUsuario, String ipAddress) {
        try {
            AuditLog auditLog = AuditLog.criar(
                idUsuario,
                nomeUsuario,
                TipoOperacao.LOGIN,
                "Usuario",
                idUsuario,
                "Login realizado com sucesso"
            );
            
            auditLog.adicionarContextoHttp(ipAddress, null);
            
            auditLogRepository.save(auditLog);
            
        } catch (Exception e) {
            log.error("Erro ao registrar login", e);
        }
    }
    
    /**
     * Registra logout de usuário
     */
    public void registrarLogout(Long idUsuario, String nomeUsuario) {
        try {
            AuditLog auditLog = AuditLog.criar(
                idUsuario,
                nomeUsuario,
                TipoOperacao.LOGOUT,
                "Usuario",
                idUsuario,
                "Logout realizado"
            );
            
            auditLogRepository.save(auditLog);
            
        } catch (Exception e) {
            log.error("Erro ao registrar logout", e);
        }
    }
}


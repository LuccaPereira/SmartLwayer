package smartLegalApi.application.audit.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.application.audit.dto.response.AuditLogResponse;
import smartLegalApi.domain.audit.entity.AuditLog;

/**
 * Mapper entre entidade de domínio AuditLog e DTOs
 */
@Component
public class AuditLogDtoMapper {
    
    public AuditLogResponse toResponse(AuditLog auditLog) {
        if (auditLog == null) return null;
        
        return AuditLogResponse.builder()
            .id(auditLog.getId())
            .idUsuario(auditLog.getIdUsuario())
            .nomeUsuario(auditLog.getNomeUsuario())
            .tipoOperacao(auditLog.getTipoOperacao())
            .entidade(auditLog.getEntidade())
            .idEntidade(auditLog.getIdEntidade())
            .detalhes(auditLog.getDetalhes())
            .ipAddress(auditLog.getIpAddress())
            .userAgent(auditLog.getUserAgent())
            .dataHora(auditLog.getDataHora())
            .dadosAntigos(auditLog.getDadosAntigos())
            .dadosNovos(auditLog.getDadosNovos())
            .build();
    }
}


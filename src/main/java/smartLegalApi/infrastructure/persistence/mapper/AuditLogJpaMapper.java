package smartLegalApi.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import smartLegalApi.domain.audit.entity.AuditLog;
import smartLegalApi.infrastructure.persistence.jpa.entity.AuditLogJpaEntity;

/**
 * Mapper entre AuditLog de domínio e JPA
 */
@Component
public class AuditLogJpaMapper {
    
    public AuditLogJpaEntity toJpaEntity(AuditLog auditLog) {
        if (auditLog == null) return null;
        
        return AuditLogJpaEntity.builder()
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
    
    public AuditLog toDomain(AuditLogJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        
        return AuditLog.builder()
            .id(jpaEntity.getId())
            .idUsuario(jpaEntity.getIdUsuario())
            .nomeUsuario(jpaEntity.getNomeUsuario())
            .tipoOperacao(jpaEntity.getTipoOperacao())
            .entidade(jpaEntity.getEntidade())
            .idEntidade(jpaEntity.getIdEntidade())
            .detalhes(jpaEntity.getDetalhes())
            .ipAddress(jpaEntity.getIpAddress())
            .userAgent(jpaEntity.getUserAgent())
            .dataHora(jpaEntity.getDataHora())
            .dadosAntigos(jpaEntity.getDadosAntigos())
            .dadosNovos(jpaEntity.getDadosNovos())
            .build();
    }
}


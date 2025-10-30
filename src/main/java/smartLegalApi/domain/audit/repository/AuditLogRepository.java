package smartLegalApi.domain.audit.repository;

import smartLegalApi.domain.audit.entity.AuditLog;
import smartLegalApi.domain.audit.valueobject.TipoOperacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório de domínio para AuditLog
 */
public interface AuditLogRepository {
    
    /**
     * Salva um log de auditoria
     */
    AuditLog save(AuditLog auditLog);
    
    /**
     * Busca log por ID
     */
    Optional<AuditLog> findById(Long id);
    
    /**
     * Lista todos os logs
     */
    List<AuditLog> findAll();
    
    /**
     * Lista logs por usuário
     */
    List<AuditLog> findByUsuario(Long idUsuario);
    
    /**
     * Lista logs por entidade
     */
    List<AuditLog> findByEntidade(String entidade, Long idEntidade);
    
    /**
     * Lista logs por tipo de operação
     */
    List<AuditLog> findByTipoOperacao(TipoOperacao tipoOperacao);
    
    /**
     * Lista logs por período
     */
    List<AuditLog> findByPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    /**
     * Lista logs recentes (últimos N registros)
     */
    List<AuditLog> findRecentes(int limit);
}


package smartLegalApi.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartLegalApi.domain.audit.valueobject.TipoOperacao;
import smartLegalApi.infrastructure.persistence.jpa.entity.AuditLogJpaEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório JPA para AuditLog
 */
@Repository
public interface AuditLogJpaRepository extends JpaRepository<AuditLogJpaEntity, Long> {
    
    List<AuditLogJpaEntity> findByIdUsuarioOrderByDataHoraDesc(Long idUsuario);
    
    List<AuditLogJpaEntity> findByEntidadeAndIdEntidadeOrderByDataHoraDesc(String entidade, Long idEntidade);
    
    List<AuditLogJpaEntity> findByTipoOperacaoOrderByDataHoraDesc(TipoOperacao tipoOperacao);
    
    List<AuditLogJpaEntity> findByDataHoraBetweenOrderByDataHoraDesc(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    @Query("SELECT a FROM AuditLogJpaEntity a ORDER BY a.dataHora DESC")
    List<AuditLogJpaEntity> findRecentesOrderByDataHoraDesc();
}


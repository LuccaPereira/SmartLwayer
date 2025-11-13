package smartLegalApi.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import smartLegalApi.domain.audit.entity.AuditLog;
import smartLegalApi.domain.audit.repository.AuditLogRepository;
import smartLegalApi.domain.audit.valueobject.TipoOperacao;
import smartLegalApi.infrastructure.persistence.jpa.repository.AuditLogJpaRepository;
import smartLegalApi.infrastructure.persistence.mapper.AuditLogJpaMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter do repositório de AuditLog
 */
@Component
@RequiredArgsConstructor
public class AuditLogRepositoryAdapter implements AuditLogRepository {
    
    private final AuditLogJpaRepository jpaRepository;
    private final AuditLogJpaMapper mapper;
    
    @Override
    public AuditLog save(AuditLog auditLog) {
        var jpaEntity = mapper.toJpaEntity(auditLog);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<AuditLog> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public List<AuditLog> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<AuditLog> findByUsuario(Long idUsuario) {
        return jpaRepository.findByIdUsuarioOrderByDataHoraDesc(idUsuario).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<AuditLog> findByEntidade(String entidade, Long idEntidade) {
        return jpaRepository.findByEntidadeAndIdEntidadeOrderByDataHoraDesc(entidade, idEntidade).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<AuditLog> findByTipoOperacao(TipoOperacao tipoOperacao) {
        return jpaRepository.findByTipoOperacaoOrderByDataHoraDesc(tipoOperacao).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<AuditLog> findByPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return jpaRepository.findByDataHoraBetweenOrderByDataHoraDesc(dataInicio, dataFim).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<AuditLog> findRecentes(int limit) {
        return jpaRepository.findRecentesOrderByDataHoraDesc().stream()
            .limit(limit)
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}


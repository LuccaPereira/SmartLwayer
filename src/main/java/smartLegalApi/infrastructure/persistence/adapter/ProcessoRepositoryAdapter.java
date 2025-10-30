package smartLegalApi.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.repository.ProcessoRepository;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;
import smartLegalApi.infrastructure.persistence.jpa.repository.ProcessoJpaRepository;
import smartLegalApi.infrastructure.persistence.mapper.ProcessoJpaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter do repositório de Processo
 */
@Component
@RequiredArgsConstructor
public class ProcessoRepositoryAdapter implements ProcessoRepository {
    
    private final ProcessoJpaRepository jpaRepository;
    private final ProcessoJpaMapper mapper;
    
    @Override
    public Processo save(Processo processo) {
        var jpaEntity = mapper.toJpaEntity(processo);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Processo update(Processo processo) {
        var jpaEntity = mapper.toJpaEntity(processo);
        var updated = jpaRepository.save(jpaEntity);
        return mapper.toDomain(updated);
    }
    
    @Override
    public Optional<Processo> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public Optional<Processo> findByNumeroProcesso(String numeroProcesso) {
        return jpaRepository.findByNumeroProcesso(numeroProcesso).map(mapper::toDomain);
    }
    
    @Override
    public List<Processo> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Processo> findByAdvogado(Long idAdvogado) {
        return jpaRepository.findByAdvogadoOrdenadoPorData(idAdvogado).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Processo> findByCliente(Long idCliente) {
        return jpaRepository.findByIdCliente(idCliente).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Processo> findByStatus(StatusProcesso status) {
        return jpaRepository.findByStatus(status).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Processo> findByAdvogadoAndStatus(Long idAdvogado, StatusProcesso status) {
        return jpaRepository.findByIdAdvogadoAndStatus(idAdvogado, status).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByNumeroProcesso(String numeroProcesso) {
        return jpaRepository.existsByNumeroProcesso(numeroProcesso);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}


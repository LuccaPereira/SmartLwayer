package smartLegalApi.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smartLegalApi.domain.processo.entity.Andamento;
import smartLegalApi.domain.processo.repository.AndamentoRepository;
import smartLegalApi.infrastructure.persistence.jpa.repository.AndamentoJpaRepository;
import smartLegalApi.infrastructure.persistence.mapper.AndamentoJpaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter do repositório de Andamento
 */
@Component
@RequiredArgsConstructor
public class AndamentoRepositoryAdapter implements AndamentoRepository {
    
    private final AndamentoJpaRepository jpaRepository;
    private final AndamentoJpaMapper mapper;
    
    @Override
    public Andamento save(Andamento andamento) {
        var jpaEntity = mapper.toJpaEntity(andamento);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Andamento update(Andamento andamento) {
        var jpaEntity = mapper.toJpaEntity(andamento);
        var updated = jpaRepository.save(jpaEntity);
        return mapper.toDomain(updated);
    }
    
    @Override
    public Optional<Andamento> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public List<Andamento> findByProcesso(Long idProcesso) {
        return jpaRepository.findByIdProcesso(idProcesso).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Andamento> findByProcessoOrdenadoPorData(Long idProcesso) {
        return jpaRepository.findByProcessoOrdenadoPorData(idProcesso).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}


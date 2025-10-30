package smartLegalApi.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.repository.PeticaoRepository;
import smartLegalApi.domain.peticao.valueobject.StatusPeticao;
import smartLegalApi.domain.peticao.valueobject.TipoPeticao;
import smartLegalApi.infrastructure.persistence.jpa.repository.PeticaoJpaRepository;
import smartLegalApi.infrastructure.persistence.mapper.PeticaoJpaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter do repositório de Petição
 */
@Component
@RequiredArgsConstructor
public class PeticaoRepositoryAdapter implements PeticaoRepository {
    
    private final PeticaoJpaRepository jpaRepository;
    private final PeticaoJpaMapper mapper;
    
    @Override
    public Peticao save(Peticao peticao) {
        var jpaEntity = mapper.toJpaEntity(peticao);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Peticao update(Peticao peticao) {
        var jpaEntity = mapper.toJpaEntity(peticao);
        var updated = jpaRepository.save(jpaEntity);
        return mapper.toDomain(updated);
    }
    
    @Override
    public Optional<Peticao> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public List<Peticao> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Peticao> findByProcesso(Long idProcesso) {
        return jpaRepository.findByProcessoOrdenadoPorData(idProcesso).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Peticao> findByAdvogado(Long idAdvogado) {
        return jpaRepository.findByAdvogadoOrdenadoPorData(idAdvogado).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Peticao> findByAdvogadoAndStatus(Long idAdvogado, StatusPeticao status) {
        return jpaRepository.findByIdAdvogadoAndStatus(idAdvogado, status).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Peticao> findByProcessoAndTipo(Long idProcesso, TipoPeticao tipo) {
        return jpaRepository.findByIdProcessoAndTipo(idProcesso, tipo).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}


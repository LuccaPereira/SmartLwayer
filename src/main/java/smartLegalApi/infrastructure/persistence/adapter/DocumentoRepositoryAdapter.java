package smartLegalApi.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import smartLegalApi.domain.processo.entity.Documento;
import smartLegalApi.domain.processo.repository.DocumentoRepository;
import smartLegalApi.infrastructure.persistence.jpa.repository.DocumentoJpaRepository;
import smartLegalApi.infrastructure.persistence.mapper.DocumentoJpaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter do repositório de Documento
 */
@Component
@RequiredArgsConstructor
public class DocumentoRepositoryAdapter implements DocumentoRepository {
    
    private final DocumentoJpaRepository jpaRepository;
    private final DocumentoJpaMapper mapper;
    
    @Override
    public Documento save(Documento documento) {
        var jpaEntity = mapper.toJpaEntity(documento);
        var saved = jpaRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Documento> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public List<Documento> findByProcesso(Long idProcesso) {
        return jpaRepository.findByIdProcesso(idProcesso).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Documento> findByProcessoAndTipo(Long idProcesso, String tipoArquivo) {
        return jpaRepository.findByIdProcessoAndTipoArquivo(idProcesso, tipoArquivo).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}


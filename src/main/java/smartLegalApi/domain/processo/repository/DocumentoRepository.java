package smartLegalApi.domain.processo.repository;

import smartLegalApi.domain.processo.entity.Documento;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepository {
    
    Documento save(Documento documento);
    
    Optional<Documento> findById(Long id);
    
    List<Documento> findByProcesso(Long idProcesso);
    
    List<Documento> findByProcessoAndTipo(Long idProcesso, String tipoArquivo);
    
    void deleteById(Long id);
}


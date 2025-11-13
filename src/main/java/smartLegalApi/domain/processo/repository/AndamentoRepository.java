package smartLegalApi.domain.processo.repository;

import smartLegalApi.domain.processo.entity.Andamento;

import java.util.List;
import java.util.Optional;

public interface AndamentoRepository {
    
    Andamento save(Andamento andamento);
    
    Andamento update(Andamento andamento);
    
    Optional<Andamento> findById(Long id);
    
    List<Andamento> findByProcesso(Long idProcesso);
    
    List<Andamento> findByProcessoOrdenadoPorData(Long idProcesso);
    
    void deleteById(Long id);
}


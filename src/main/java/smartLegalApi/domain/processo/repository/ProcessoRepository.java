package smartLegalApi.domain.processo.repository;

import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;

import java.util.List;
import java.util.Optional;

public interface ProcessoRepository {
    
    Processo save(Processo processo);
    
    Processo update(Processo processo);
    
    Optional<Processo> findById(Long id);
    
    Optional<Processo> findByNumeroProcesso(String numeroProcesso);
    
    List<Processo> findAll();
    
    List<Processo> findByAdvogado(Long idAdvogado);
    
    List<Processo> findByCliente(Long idCliente);
    
    List<Processo> findByStatus(StatusProcesso status);
    
    List<Processo> findByAdvogadoAndStatus(Long idAdvogado, StatusProcesso status);
    
    boolean existsByNumeroProcesso(String numeroProcesso);
    
    void deleteById(Long id);
}


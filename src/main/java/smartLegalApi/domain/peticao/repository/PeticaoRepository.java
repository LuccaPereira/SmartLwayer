package smartLegalApi.domain.peticao.repository;

import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.valueobject.StatusPeticao;
import smartLegalApi.domain.peticao.valueobject.TipoPeticao;

import java.util.List;
import java.util.Optional;

/**
 * Repositório de domínio para Petição
 */
public interface PeticaoRepository {
    
    /**
     * Salva uma nova petição
     */
    Peticao save(Peticao peticao);
    
    /**
     * Atualiza uma petição existente
     */
    Peticao update(Peticao peticao);
    
    /**
     * Busca petição por ID
     */
    Optional<Peticao> findById(Long id);
    
    /**
     * Lista todas as petições
     */
    List<Peticao> findAll();
    
    /**
     * Lista petições por processo
     */
    List<Peticao> findByProcesso(Long idProcesso);
    
    /**
     * Lista petições por advogado
     */
    List<Peticao> findByAdvogado(Long idAdvogado);
    
    /**
     * Lista petições por advogado e status
     */
    List<Peticao> findByAdvogadoAndStatus(Long idAdvogado, StatusPeticao status);
    
    /**
     * Lista petições por processo e tipo
     */
    List<Peticao> findByProcessoAndTipo(Long idProcesso, TipoPeticao tipo);
    
    /**
     * Deleta uma petição
     */
    void deleteById(Long id);
}


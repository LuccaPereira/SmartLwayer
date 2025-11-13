package smartLegalApi.domain.advogado.repository;

import smartLegalApi.domain.advogado.entity.Advogado;

import java.util.List;
import java.util.Optional;

/**
 * Interface do repositório de Advogado (Domínio)
 * Define o contrato que a infraestrutura deve implementar
 */
public interface AdvogadoRepository {
    
    /**
     * Salva um novo advogado
     */
    Advogado save(Advogado advogado);
    
    /**
     * Atualiza um advogado existente
     */
    Advogado update(Advogado advogado);
    
    /**
     * Busca advogado por ID
     */
    Optional<Advogado> findById(Long id);
    
    /**
     * Busca advogado por OAB
     */
    Optional<Advogado> findByOAB(String oab);
    
    /**
     * Busca advogado por email
     */
    Optional<Advogado> findByEmail(String email);
    
    /**
     * Busca advogado por CPF
     */
    Optional<Advogado> findByCPF(String cpf);
    
    /**
     * Lista todos os advogados ativos
     */
    List<Advogado> findAllAtivos();
    
    /**
     * Lista todos os advogados (ativos e inativos)
     */
    List<Advogado> findAll();
    
    /**
     * Verifica se já existe advogado com essa OAB
     */
    boolean existsByOAB(String oab);
    
    /**
     * Verifica se já existe advogado com esse email
     */
    boolean existsByEmail(String email);
    
    /**
     * Deleta advogado por ID (hard delete - use com cuidado!)
     */
    void deleteById(Long id);
}


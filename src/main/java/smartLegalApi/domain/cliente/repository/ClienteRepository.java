package smartLegalApi.domain.cliente.repository;

import smartLegalApi.domain.cliente.entity.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Interface do repositório de Cliente (Domínio)
 */
public interface ClienteRepository {
    
    /**
     * Salva um novo cliente
     */
    Cliente save(Cliente cliente);
    
    /**
     * Atualiza um cliente existente
     */
    Cliente update(Cliente cliente);
    
    /**
     * Busca cliente por ID
     */
    Optional<Cliente> findById(Long id);
    
    /**
     * Busca cliente por CPF/CNPJ
     */
    Optional<Cliente> findByCpfCnpj(String cpfCnpj);
    
    /**
     * Busca cliente por email
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Lista todos os clientes ativos
     */
    List<Cliente> findAllAtivos();
    
    /**
     * Lista todos os clientes (ativos e inativos)
     */
    List<Cliente> findAll();
    
    /**
     * Verifica se já existe cliente com esse CPF/CNPJ
     */
    boolean existsByCpfCnpj(String cpfCnpj);
    
    /**
     * Deleta cliente por ID (hard delete - use com cuidado!)
     */
    void deleteById(Long id);
}


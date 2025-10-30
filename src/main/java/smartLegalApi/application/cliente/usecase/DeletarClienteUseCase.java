package smartLegalApi.application.cliente.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.cliente.exception.ClienteNaoEncontradoException;
import smartLegalApi.domain.cliente.repository.ClienteRepository;

/**
 * Caso de Uso: Deletar Cliente (Hard Delete)
 * ATENÇÃO: Usar com cuidado! Prefer usar InativarClienteUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeletarClienteUseCase {
    
    private final ClienteRepository clienteRepository;
    
    @Transactional
    @CacheEvict(value = "clientes", allEntries = true)
    public void executar(Long id) {
        log.warn("HARD DELETE: Deletando cliente ID: {}", id);
        
        // Verificar se existe
        clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        
        // Deletar
        clienteRepository.deleteById(id);
        
        log.warn("Cliente deletado permanentemente. ID: {}", id);
    }
}


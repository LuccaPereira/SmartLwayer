package smartLegalApi.application.cliente.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.exception.ClienteNaoEncontradoException;
import smartLegalApi.domain.cliente.repository.ClienteRepository;

/**
 * Caso de Uso: Inativar Cliente (Soft Delete)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InativarClienteUseCase {
    
    private final ClienteRepository clienteRepository;
    
    @Transactional
    @CacheEvict(value = "clientes", allEntries = true)
    public void executar(Long id) {
        log.info("Inativando cliente ID: {}", id);
        
        // Buscar cliente
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        
        // Inativar
        cliente.inativar();
        
        // Salvar
        clienteRepository.update(cliente);
        
        log.info("Cliente inativado com sucesso. ID: {}", id);
    }
}


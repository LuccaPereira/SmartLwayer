package smartLegalApi.application.cliente.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.exception.ClienteNaoEncontradoException;
import smartLegalApi.domain.cliente.repository.ClienteRepository;

/**
 * Caso de Uso: Buscar Cliente por ID
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarClientePorIdUseCase {
    
    private final ClienteRepository clienteRepository;
    
    @Transactional(readOnly = true)
    public Cliente executar(Long id) {
        log.debug("Buscando cliente por ID: {}", id);
        
        return clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }
}


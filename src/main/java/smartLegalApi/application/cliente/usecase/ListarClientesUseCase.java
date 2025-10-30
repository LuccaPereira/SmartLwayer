package smartLegalApi.application.cliente.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.repository.ClienteRepository;

import java.util.List;

/**
 * Caso de Uso: Listar Clientes
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ListarClientesUseCase {
    
    private final ClienteRepository clienteRepository;
    
    @Transactional(readOnly = true)
    @Cacheable(value = "clientes", key = "'apenasAtivos_' + #apenasAtivos")
    public List<Cliente> executar(boolean apenasAtivos) {
        log.debug("Listando clientes. Apenas ativos: {}", apenasAtivos);
        
        if (apenasAtivos) {
            return clienteRepository.findAllAtivos();
        }
        
        return clienteRepository.findAll();
    }
}


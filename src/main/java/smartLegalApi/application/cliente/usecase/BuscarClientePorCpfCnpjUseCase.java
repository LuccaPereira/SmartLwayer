package smartLegalApi.application.cliente.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.cliente.entity.Cliente;
import smartLegalApi.domain.cliente.exception.ClienteNaoEncontradoException;
import smartLegalApi.domain.cliente.repository.ClienteRepository;

/**
 * Caso de Uso: Buscar Cliente por CPF/CNPJ
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarClientePorCpfCnpjUseCase {
    
    private final ClienteRepository clienteRepository;
    
    @Transactional(readOnly = true)
    public Cliente executar(String cpfCnpj) {
        log.debug("Buscando cliente por CPF/CNPJ: {}", cpfCnpj);
        
        return clienteRepository.findByCpfCnpj(cpfCnpj)
            .orElseThrow(() -> new ClienteNaoEncontradoException(cpfCnpj));
    }
}


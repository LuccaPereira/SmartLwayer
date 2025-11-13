package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.repository.ProcessoRepository;

import java.util.List;

/**
 * Caso de uso: Listar Processos por Cliente
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ListarProcessosPorClienteUseCase {
    
    private final ProcessoRepository processoRepository;
    
    @Transactional(readOnly = true)
    public List<Processo> executar(Long idCliente) {
        log.info("Listando processos do cliente ID: {}", idCliente);
        
        return processoRepository.findByCliente(idCliente);
    }
}


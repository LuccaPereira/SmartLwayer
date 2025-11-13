package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.repository.AndamentoRepository;
import smartLegalApi.domain.shared.exception.NotFoundException;

/**
 * Caso de uso: Deletar Andamento
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeletarAndamentoUseCase {
    
    private final AndamentoRepository andamentoRepository;
    
    @Transactional
    public void executar(Long id) {
        log.info("Deletando andamento ID: {}", id);
        
        if (!andamentoRepository.findById(id).isPresent()) {
            throw new NotFoundException("Andamento não encontrado com ID: " + id);
        }
        
        andamentoRepository.deleteById(id);
        log.info("Andamento deletado com sucesso. ID: {}", id);
    }
}


package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.exception.ProcessoNaoEncontradoException;
import smartLegalApi.domain.processo.repository.ProcessoRepository;

/**
 * Caso de uso: Deletar Processo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeletarProcessoUseCase {
    
    private final ProcessoRepository processoRepository;
    
    @Transactional
    public void executar(Long id) {
        log.info("Deletando processo ID: {}", id);
        
        if (!processoRepository.findById(id).isPresent()) {
            throw new ProcessoNaoEncontradoException(id);
        }
        
        processoRepository.deleteById(id);
        log.info("Processo deletado com sucesso. ID: {}", id);
    }
}


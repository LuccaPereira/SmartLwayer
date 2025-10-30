package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.exception.ProcessoNaoEncontradoException;
import smartLegalApi.domain.processo.repository.ProcessoRepository;

/**
 * Caso de uso: Atualizar Processo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AtualizarProcessoUseCase {
    
    private final ProcessoRepository processoRepository;
    
    @Transactional
    public Processo executar(Long id, String titulo, String descricao) {
        log.info("Atualizando processo ID: {}", id);
        
        Processo processo = processoRepository.findById(id)
            .orElseThrow(() -> new ProcessoNaoEncontradoException(id));
        
        processo.atualizar(titulo, descricao);
        
        Processo updated = processoRepository.update(processo);
        log.info("Processo atualizado com sucesso. ID: {}", id);
        
        return updated;
    }
}


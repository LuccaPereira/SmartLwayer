package smartLegalApi.application.advogado.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.exception.AdvogadoNaoEncontradoException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;

/**
 * Caso de Uso: Deletar Advogado (Hard Delete)
 * ATENÇÃO: Usar com cuidado! Prefer usar InativarAdvogadoUseCase
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeletarAdvogadoUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    
    @Transactional
    @CacheEvict(value = "advogados", allEntries = true)
    public void executar(Long id) {
        log.warn("HARD DELETE: Deletando advogado ID: {}", id);
        
        // Verificar se existe
        advogadoRepository.findById(id)
            .orElseThrow(() -> new AdvogadoNaoEncontradoException(id));
        
        // Deletar
        advogadoRepository.deleteById(id);
        
        log.warn("Advogado deletado permanentemente. ID: {}", id);
    }
}


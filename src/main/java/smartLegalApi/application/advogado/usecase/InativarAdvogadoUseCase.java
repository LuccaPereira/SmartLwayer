package smartLegalApi.application.advogado.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.exception.AdvogadoNaoEncontradoException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;

/**
 * Caso de Uso: Inativar Advogado (Soft Delete)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InativarAdvogadoUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    
    @Transactional
    @CacheEvict(value = "advogados", allEntries = true)
    public void executar(Long id) {
        log.info("Inativando advogado ID: {}", id);
        
        // Buscar advogado
        Advogado advogado = advogadoRepository.findById(id)
            .orElseThrow(() -> new AdvogadoNaoEncontradoException(id));
        
        // Inativar
        advogado.inativar();
        
        // Salvar
        advogadoRepository.update(advogado);
        
        log.info("Advogado inativado com sucesso. ID: {}", id);
    }
}


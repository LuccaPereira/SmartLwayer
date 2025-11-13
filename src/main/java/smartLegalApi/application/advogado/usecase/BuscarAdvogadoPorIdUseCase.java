package smartLegalApi.application.advogado.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.exception.AdvogadoNaoEncontradoException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;

/**
 * Caso de Uso: Buscar Advogado por ID
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarAdvogadoPorIdUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    
    @Transactional(readOnly = true)
    public Advogado executar(Long id) {
        log.debug("Buscando advogado por ID: {}", id);
        
        return advogadoRepository.findById(id)
            .orElseThrow(() -> new AdvogadoNaoEncontradoException(id));
    }
}


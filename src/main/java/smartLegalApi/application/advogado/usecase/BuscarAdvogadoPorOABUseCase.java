package smartLegalApi.application.advogado.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.exception.AdvogadoNaoEncontradoException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;

/**
 * Caso de Uso: Buscar Advogado por OAB
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BuscarAdvogadoPorOABUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    
    @Transactional(readOnly = true)
    public Advogado executar(String oab) {
        log.debug("Buscando advogado por OAB: {}", oab);
        
        return advogadoRepository.findByOAB(oab)
            .orElseThrow(() -> new AdvogadoNaoEncontradoException(oab));
    }
}


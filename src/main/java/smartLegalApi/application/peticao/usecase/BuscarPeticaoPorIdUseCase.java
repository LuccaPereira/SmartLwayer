package smartLegalApi.application.peticao.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.exception.PeticaoNaoEncontradaException;
import smartLegalApi.domain.peticao.repository.PeticaoRepository;

/**
 * Caso de uso: Buscar Petição por ID
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BuscarPeticaoPorIdUseCase {
    
    private final PeticaoRepository peticaoRepository;
    
    @Transactional(readOnly = true)
    public Peticao executar(Long id) {
        log.info("Buscando petição por ID: {}", id);
        
        return peticaoRepository.findById(id)
            .orElseThrow(() -> new PeticaoNaoEncontradaException(id));
    }
}


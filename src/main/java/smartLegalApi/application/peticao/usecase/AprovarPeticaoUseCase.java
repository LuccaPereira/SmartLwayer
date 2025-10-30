package smartLegalApi.application.peticao.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.exception.PeticaoNaoEncontradaException;
import smartLegalApi.domain.peticao.repository.PeticaoRepository;

/**
 * Caso de uso: Aprovar Petição
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AprovarPeticaoUseCase {
    
    private final PeticaoRepository peticaoRepository;
    
    @Transactional
    public Peticao executar(Long idPeticao) {
        log.info("Aprovando petição ID: {}", idPeticao);
        
        Peticao peticao = peticaoRepository.findById(idPeticao)
            .orElseThrow(() -> new PeticaoNaoEncontradaException(idPeticao));
        
        peticao.aprovar();
        
        Peticao updated = peticaoRepository.update(peticao);
        log.info("Petição aprovada com sucesso. ID: {}", idPeticao);
        
        return updated;
    }
}


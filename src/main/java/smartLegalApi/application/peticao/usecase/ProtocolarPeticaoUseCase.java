package smartLegalApi.application.peticao.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.exception.PeticaoNaoEncontradaException;
import smartLegalApi.domain.peticao.repository.PeticaoRepository;
import smartLegalApi.infrastructure.document.service.DocumentoWordService;

/**
 * Caso de uso: Protocolar Petição (gera documento e marca como protocolada)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProtocolarPeticaoUseCase {
    
    private final PeticaoRepository peticaoRepository;
    private final DocumentoWordService documentoWordService;
    
    @Transactional
    public Peticao executar(Long idPeticao) {
        log.info("Protocolando petição ID: {}", idPeticao);
        
        Peticao peticao = peticaoRepository.findById(idPeticao)
            .orElseThrow(() -> new PeticaoNaoEncontradaException(idPeticao));
        
        // Gera documento Word
        String caminhoDocumento = documentoWordService.gerarDocumento(peticao);
        
        // Marca como protocolada
        peticao.protocolar(caminhoDocumento);
        
        Peticao updated = peticaoRepository.update(peticao);
        log.info("Petição protocolada com sucesso. ID: {}, Documento: {}", idPeticao, caminhoDocumento);
        
        return updated;
    }
}


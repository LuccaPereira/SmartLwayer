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
 * Caso de uso: Deletar Petição
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeletarPeticaoUseCase {
    
    private final PeticaoRepository peticaoRepository;
    private final DocumentoWordService documentoWordService;
    
    @Transactional
    public void executar(Long idPeticao) {
        log.info("Deletando petição ID: {}", idPeticao);
        
        Peticao peticao = peticaoRepository.findById(idPeticao)
            .orElseThrow(() -> new PeticaoNaoEncontradaException(idPeticao));
        
        // Exclui documento se existir
        if (peticao.getCaminhoDocumento() != null) {
            documentoWordService.excluirDocumento(peticao.getCaminhoDocumento());
        }
        
        peticaoRepository.deleteById(idPeticao);
        log.info("Petição deletada com sucesso. ID: {}", idPeticao);
    }
}


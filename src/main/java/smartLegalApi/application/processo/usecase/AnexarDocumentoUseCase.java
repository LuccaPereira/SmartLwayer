package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.entity.Documento;
import smartLegalApi.domain.processo.exception.ProcessoNaoEncontradoException;
import smartLegalApi.domain.processo.repository.DocumentoRepository;
import smartLegalApi.domain.processo.repository.ProcessoRepository;

/**
 * Caso de uso: Anexar Documento ao Processo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AnexarDocumentoUseCase {
    
    private final DocumentoRepository documentoRepository;
    private final ProcessoRepository processoRepository;
    
    @Transactional
    public Documento executar(Long idProcesso, String nomeDocumento, String caminhoArquivo, 
                              String tipoArquivo, Long tamanhoBytes, String hashArquivo) {
        log.info("Anexando documento ao processo ID: {}", idProcesso);
        
        // Validar processo existe
        processoRepository.findById(idProcesso)
            .orElseThrow(() -> new ProcessoNaoEncontradoException(idProcesso));
        
        // Criar documento
        Documento documento = Documento.criar(
            idProcesso, 
            nomeDocumento, 
            caminhoArquivo, 
            tipoArquivo, 
            tamanhoBytes, 
            hashArquivo
        );
        
        Documento saved = documentoRepository.save(documento);
        log.info("Documento anexado com sucesso. ID: {}", saved.getId());
        
        return saved;
    }
}


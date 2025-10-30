package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.entity.Documento;
import smartLegalApi.domain.processo.repository.DocumentoRepository;

import java.util.List;

/**
 * Caso de uso: Listar Documentos do Processo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ListarDocumentosUseCase {
    
    private final DocumentoRepository documentoRepository;
    
    @Transactional(readOnly = true)
    public List<Documento> executar(Long idProcesso, String tipoArquivo) {
        log.info("Listando documentos do processo ID: {}", idProcesso);
        
        if (tipoArquivo != null && !tipoArquivo.isBlank()) {
            return documentoRepository.findByProcessoAndTipo(idProcesso, tipoArquivo);
        }
        
        return documentoRepository.findByProcesso(idProcesso);
    }
}


package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.repository.DocumentoRepository;
import smartLegalApi.domain.shared.exception.NotFoundException;

/**
 * Caso de uso: Deletar Documento
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeletarDocumentoUseCase {
    
    private final DocumentoRepository documentoRepository;
    
    @Transactional
    public void executar(Long id) {
        log.info("Deletando documento ID: {}", id);
        
        if (!documentoRepository.findById(id).isPresent()) {
            throw new NotFoundException("Documento não encontrado com ID: " + id);
        }
        
        documentoRepository.deleteById(id);
        log.info("Documento deletado com sucesso. ID: {}", id);
    }
}


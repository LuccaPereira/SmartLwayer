package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.repository.ProcessoRepository;
import smartLegalApi.domain.processo.valueobject.StatusProcesso;

import java.util.List;

/**
 * Caso de uso: Listar Processos por Advogado
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ListarProcessosPorAdvogadoUseCase {
    
    private final ProcessoRepository processoRepository;
    
    @Transactional(readOnly = true)
    public List<Processo> executar(Long idAdvogado, StatusProcesso status) {
        log.info("Listando processos do advogado ID: {}, Status: {}", idAdvogado, status);
        
        if (status != null) {
            return processoRepository.findByAdvogadoAndStatus(idAdvogado, status);
        }
        
        return processoRepository.findByAdvogado(idAdvogado);
    }
}


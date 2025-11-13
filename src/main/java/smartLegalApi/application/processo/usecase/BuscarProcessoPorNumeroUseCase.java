package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.exception.ProcessoNaoEncontradoException;
import smartLegalApi.domain.processo.repository.ProcessoRepository;

/**
 * Caso de uso: Buscar Processo por Número
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BuscarProcessoPorNumeroUseCase {
    
    private final ProcessoRepository processoRepository;
    
    @Transactional(readOnly = true)
    public Processo executar(String numeroProcesso) {
        log.info("Buscando processo por número: {}", numeroProcesso);
        
        return processoRepository.findByNumeroProcesso(numeroProcesso)
            .orElseThrow(() -> new ProcessoNaoEncontradoException("Número: " + numeroProcesso));
    }
}


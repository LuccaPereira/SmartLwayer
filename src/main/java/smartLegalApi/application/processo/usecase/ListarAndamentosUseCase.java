package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.entity.Andamento;
import smartLegalApi.domain.processo.repository.AndamentoRepository;

import java.util.List;

/**
 * Caso de uso: Listar Andamentos do Processo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ListarAndamentosUseCase {
    
    private final AndamentoRepository andamentoRepository;
    
    @Transactional(readOnly = true)
    public List<Andamento> executar(Long idProcesso) {
        log.info("Listando andamentos do processo ID: {}", idProcesso);
        
        return andamentoRepository.findByProcessoOrdenadoPorData(idProcesso);
    }
}


package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.entity.Andamento;
import smartLegalApi.domain.processo.repository.AndamentoRepository;
import smartLegalApi.domain.shared.exception.NotFoundException;

import java.time.LocalDateTime;

/**
 * Caso de uso: Atualizar Andamento
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AtualizarAndamentoUseCase {
    
    private final AndamentoRepository andamentoRepository;
    
    @Transactional
    public Andamento executar(Long id, LocalDateTime dataAndamento, String descricao, String tipo) {
        log.info("Atualizando andamento ID: {}", id);
        
        Andamento andamento = andamentoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Andamento não encontrado com ID: " + id));
        
        andamento.atualizar(dataAndamento, descricao, tipo);
        
        Andamento updated = andamentoRepository.update(andamento);
        log.info("Andamento atualizado com sucesso. ID: {}", id);
        
        return updated;
    }
}


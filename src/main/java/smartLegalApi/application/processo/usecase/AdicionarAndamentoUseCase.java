package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.processo.entity.Andamento;
import smartLegalApi.domain.processo.exception.ProcessoNaoEncontradoException;
import smartLegalApi.domain.processo.repository.AndamentoRepository;
import smartLegalApi.domain.processo.repository.ProcessoRepository;

import java.time.LocalDateTime;

/**
 * Caso de uso: Adicionar Andamento ao Processo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdicionarAndamentoUseCase {
    
    private final AndamentoRepository andamentoRepository;
    private final ProcessoRepository processoRepository;
    
    @Transactional
    public Andamento executar(Long idProcesso, LocalDateTime dataAndamento, String descricao, String tipo) {
        log.info("Adicionando andamento ao processo ID: {}", idProcesso);
        
        // Validar processo existe
        processoRepository.findById(idProcesso)
            .orElseThrow(() -> new ProcessoNaoEncontradoException(idProcesso));
        
        // Criar andamento
        Andamento andamento = Andamento.criar(idProcesso, dataAndamento, descricao, tipo);
        
        Andamento saved = andamentoRepository.save(andamento);
        log.info("Andamento adicionado com sucesso. ID: {}", saved.getId());
        
        return saved;
    }
}


package smartLegalApi.application.peticao.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.repository.PeticaoRepository;
import smartLegalApi.domain.peticao.valueobject.TipoPeticao;
import smartLegalApi.domain.processo.repository.ProcessoRepository;
import smartLegalApi.domain.shared.exception.NotFoundException;

/**
 * Caso de uso: Criar Petição
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CriarPeticaoUseCase {
    
    private final PeticaoRepository peticaoRepository;
    private final ProcessoRepository processoRepository;
    private final AdvogadoRepository advogadoRepository;
    
    @Transactional
    public Peticao executar(Long idProcesso, Long idAdvogado, TipoPeticao tipo, String titulo) {
        log.info("Criando petição para processo ID: {}", idProcesso);
        
        // Valida processo existe
        processoRepository.findById(idProcesso)
            .orElseThrow(() -> new NotFoundException("Processo não encontrado com ID: " + idProcesso));
        
        // Valida advogado existe
        advogadoRepository.findById(idAdvogado)
            .orElseThrow(() -> new NotFoundException("Advogado não encontrado com ID: " + idAdvogado));
        
        // Cria petição em rascunho
        Peticao peticao = Peticao.criar(idProcesso, idAdvogado, tipo, titulo);
        
        Peticao saved = peticaoRepository.save(peticao);
        log.info("Petição criada com sucesso. ID: {}", saved.getId());
        
        return saved;
    }
}


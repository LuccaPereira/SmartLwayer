package smartLegalApi.application.processo.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.domain.cliente.repository.ClienteRepository;
import smartLegalApi.domain.processo.entity.Processo;
import smartLegalApi.domain.processo.exception.NumeroProcessoJaCadastradoException;
import smartLegalApi.domain.processo.repository.ProcessoRepository;
import smartLegalApi.domain.processo.valueobject.NumeroProcesso;
import smartLegalApi.domain.shared.exception.NotFoundException;

/**
 * Caso de uso: Criar Processo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CriarProcessoUseCase {
    
    private final ProcessoRepository processoRepository;
    private final AdvogadoRepository advogadoRepository;
    private final ClienteRepository clienteRepository;
    
    @Transactional
    public Processo executar(String numeroProcesso, String titulo, String descricao, 
                             Long idAdvogado, Long idCliente) {
        
        log.info("Criando processo: {}", numeroProcesso);
        
        // Validar advogado existe
        advogadoRepository.findById(idAdvogado)
            .orElseThrow(() -> new NotFoundException("Advogado não encontrado com ID: " + idAdvogado));
        
        // Validar cliente existe
        clienteRepository.findById(idCliente)
            .orElseThrow(() -> new NotFoundException("Cliente não encontrado com ID: " + idCliente));
        
        // Validar número do processo único
        if (processoRepository.existsByNumeroProcesso(numeroProcesso)) {
            throw new NumeroProcessoJaCadastradoException(numeroProcesso);
        }
        
        // Criar processo
        Processo processo = Processo.criar(
            new NumeroProcesso(numeroProcesso),
            titulo,
            descricao,
            idAdvogado,
            idCliente
        );
        
        Processo saved = processoRepository.save(processo);
        log.info("Processo criado com sucesso. ID: {}", saved.getId());
        
        return saved;
    }
}


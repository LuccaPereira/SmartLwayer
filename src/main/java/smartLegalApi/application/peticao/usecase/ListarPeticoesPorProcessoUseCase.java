package smartLegalApi.application.peticao.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.repository.PeticaoRepository;

import java.util.List;

/**
 * Caso de uso: Listar Petições por Processo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ListarPeticoesPorProcessoUseCase {
    
    private final PeticaoRepository peticaoRepository;
    
    @Transactional(readOnly = true)
    public List<Peticao> executar(Long idProcesso) {
        log.info("Listando petições do processo ID: {}", idProcesso);
        
        return peticaoRepository.findByProcesso(idProcesso);
    }
}


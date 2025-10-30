package smartLegalApi.application.peticao.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.peticao.entity.Peticao;
import smartLegalApi.domain.peticao.repository.PeticaoRepository;
import smartLegalApi.domain.peticao.valueobject.StatusPeticao;

import java.util.List;

/**
 * Caso de uso: Listar Petições por Advogado
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ListarPeticoesPorAdvogadoUseCase {
    
    private final PeticaoRepository peticaoRepository;
    
    @Transactional(readOnly = true)
    public List<Peticao> executar(Long idAdvogado, StatusPeticao status) {
        log.info("Listando petições do advogado ID: {}, Status: {}", idAdvogado, status);
        
        if (status != null) {
            return peticaoRepository.findByAdvogadoAndStatus(idAdvogado, status);
        }
        
        return peticaoRepository.findByAdvogado(idAdvogado);
    }
}


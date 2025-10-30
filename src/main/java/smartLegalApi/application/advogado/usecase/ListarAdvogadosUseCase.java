package smartLegalApi.application.advogado.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;

import java.util.List;

/**
 * Caso de Uso: Listar Advogados
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ListarAdvogadosUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    
    @Transactional(readOnly = true)
    @Cacheable(value = "advogados", key = "'apenasAtivos_' + #apenasAtivos")
    public List<Advogado> executar(boolean apenasAtivos) {
        log.debug("Listando advogados. Apenas ativos: {}", apenasAtivos);
        
        if (apenasAtivos) {
            return advogadoRepository.findAllAtivos();
        }
        
        return advogadoRepository.findAll();
    }
}


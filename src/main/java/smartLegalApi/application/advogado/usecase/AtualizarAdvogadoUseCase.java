package smartLegalApi.application.advogado.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.exception.AdvogadoNaoEncontradoException;
import smartLegalApi.domain.advogado.exception.EmailJaCadastradoException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.Telefone;

/**
 * Caso de Uso: Atualizar Dados do Advogado
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizarAdvogadoUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    
    @Transactional
    @CacheEvict(value = "advogados", allEntries = true)
    public Advogado executar(Long id, String nome, String emailStr, String telefoneStr) {
        log.info("Atualizando advogado ID: {}", id);
        
        // Buscar advogado
        Advogado advogado = advogadoRepository.findById(id)
            .orElseThrow(() -> new AdvogadoNaoEncontradoException(id));
        
        // Validar email (se mudou)
        Email novoEmail = emailStr != null ? new Email(emailStr) : null;
        if (novoEmail != null && !novoEmail.equals(advogado.getEmail())) {
            if (advogadoRepository.existsByEmail(novoEmail.toString())) {
                throw new EmailJaCadastradoException(novoEmail.toString());
            }
        }
        
        // Criar telefone
        Telefone telefone = telefoneStr != null && !telefoneStr.isBlank() ? new Telefone(telefoneStr) : null;
        
        // Atualizar
        advogado.atualizar(nome, novoEmail, telefone);
        
        // Salvar
        Advogado advogadoAtualizado = advogadoRepository.update(advogado);
        
        log.info("Advogado atualizado com sucesso. ID: {}", advogadoAtualizado.getId());
        
        return advogadoAtualizado;
    }
}


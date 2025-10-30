package smartLegalApi.application.auth.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.exception.AdvogadoNaoEncontradoException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.domain.shared.exception.BusinessRuleException;
import smartLegalApi.infrastructure.security.service.PasswordEncoderService;

/**
 * Caso de uso: Alterar senha do advogado (usuário autenticado)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlterarSenhaUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    private final PasswordEncoderService passwordEncoder;
    
    @Transactional
    public void executar(Long advogadoId, AlterarSenhaRequest request) {
        log.info("Alterando senha do advogado ID: {}", advogadoId);
        
        // Busca o advogado
        Advogado advogado = advogadoRepository.findById(advogadoId)
            .orElseThrow(() -> new AdvogadoNaoEncontradoException(advogadoId));
        
        // Valida senha atual
        if (!passwordEncoder.matches(request.senhaAtual(), advogado.getSenhaHash())) {
            log.warn("Senha atual incorreta para advogado ID: {}", advogadoId);
            throw new BadCredentialsException("Senha atual incorreta");
        }
        
        // Valida nova senha
        validarNovaSenha(request.novaSenha(), request.confirmarNovaSenha(), advogado.getSenhaHash());
        
        // Criptografa e atualiza senha
        String novaSenhaHash = passwordEncoder.encode(request.novaSenha());
        
        // Cria novo advogado com senha atualizada
        Advogado advogadoAtualizado = Advogado.builder()
            .id(advogado.getId())
            .oab(advogado.getOab())
            .nome(advogado.getNome())
            .cpf(advogado.getCpf())
            .email(advogado.getEmail())
            .telefone(advogado.getTelefone())
            .senhaHash(novaSenhaHash)
            .dataCadastro(advogado.getDataCadastro())
            .dataAtualizacao(advogado.getDataAtualizacao())
            .ativo(advogado.isAtivo())
            .build();
        
        advogadoRepository.save(advogadoAtualizado);
        
        log.info("Senha alterada com sucesso para advogado ID: {}", advogadoId);
    }
    
    private void validarNovaSenha(String novaSenha, String confirmarNovaSenha, String senhaAtualHash) {
        if (novaSenha == null || novaSenha.isBlank()) {
            throw new BusinessRuleException("Nova senha é obrigatória");
        }
        
        if (novaSenha.length() < 8) {
            throw new BusinessRuleException("Nova senha deve ter no mínimo 8 caracteres");
        }
        
        if (!novaSenha.equals(confirmarNovaSenha)) {
            throw new BusinessRuleException("As senhas não coincidem");
        }
        
        // Não pode ser igual à senha atual
        if (passwordEncoder.matches(novaSenha, senhaAtualHash)) {
            throw new BusinessRuleException("Nova senha deve ser diferente da senha atual");
        }
        
        // Validação de complexidade
        boolean temLetra = novaSenha.matches(".*[a-zA-Z].*");
        boolean temNumero = novaSenha.matches(".*\\d.*");
        
        if (!temLetra || !temNumero) {
            throw new BusinessRuleException("Nova senha deve conter letras e números");
        }
    }
    
    /**
     * Request para alterar senha
     */
    public record AlterarSenhaRequest(
        String senhaAtual,
        String novaSenha,
        String confirmarNovaSenha
    ) {}
}


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
import smartLegalApi.infrastructure.security.service.PasswordResetTokenService;

/**
 * Caso de uso: Resetar senha usando token
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResetarSenhaUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    private final PasswordResetTokenService tokenService;
    private final PasswordEncoderService passwordEncoder;
    
    @Transactional
    public void executar(ResetarSenhaRequest request) {
        log.info("Tentativa de reset de senha com token");
        
        // Valida o token e extrai o ID do advogado
        Long advogadoId = tokenService.validateAndGetUserId(request.token());
        if (advogadoId == null) {
            log.warn("Token de reset inválido ou expirado");
            throw new BadCredentialsException("Token de reset inválido ou expirado");
        }
        
        // Busca o advogado
        Advogado advogado = advogadoRepository.findById(advogadoId)
            .orElseThrow(() -> new AdvogadoNaoEncontradoException(advogadoId));
        
        // Valida nova senha
        validarNovaSenha(request.novaSenha(), request.confirmarNovaSenha());
        
        // Criptografa a nova senha
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
        
        // Invalida o token para que não possa ser reutilizado
        tokenService.invalidateToken(request.token());
        
        log.info("Senha resetada com sucesso para advogado ID: {}", advogadoId);
    }
    
    private void validarNovaSenha(String novaSenha, String confirmarNovaSenha) {
        if (novaSenha == null || novaSenha.isBlank()) {
            throw new BusinessRuleException("Nova senha é obrigatória");
        }
        
        if (novaSenha.length() < 8) {
            throw new BusinessRuleException("Nova senha deve ter no mínimo 8 caracteres");
        }
        
        if (!novaSenha.equals(confirmarNovaSenha)) {
            throw new BusinessRuleException("As senhas não coincidem");
        }
        
        // Validação de complexidade
        boolean temLetra = novaSenha.matches(".*[a-zA-Z].*");
        boolean temNumero = novaSenha.matches(".*\\d.*");
        
        if (!temLetra || !temNumero) {
            throw new BusinessRuleException("Nova senha deve conter letras e números");
        }
    }
    
    /**
     * Request para resetar senha
     */
    public record ResetarSenhaRequest(
        String token,
        String novaSenha,
        String confirmarNovaSenha
    ) {}
}


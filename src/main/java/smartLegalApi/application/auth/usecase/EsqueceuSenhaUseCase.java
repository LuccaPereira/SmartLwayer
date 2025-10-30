package smartLegalApi.application.auth.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.exception.AdvogadoNaoEncontradoException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.infrastructure.security.service.PasswordResetTokenService;

/**
 * Caso de uso: Solicitar reset de senha (esqueceu senha)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EsqueceuSenhaUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    private final PasswordResetTokenService tokenService;
    // TODO: Injetar EmailService quando implementado
    
    @Transactional(readOnly = true)
    public EsqueceuSenhaResult executar(String emailStr) {
        log.info("Solicitação de reset de senha para: {}", emailStr);
        
        // Valida e busca advogado por email
        Email email = new Email(emailStr);
        Advogado advogado = advogadoRepository.findByEmail(email.getValor())
            .orElseThrow(() -> {
                // Por segurança, não revela se o email existe ou não
                log.warn("Tentativa de reset para email não encontrado: {}", emailStr);
                return new AdvogadoNaoEncontradoException("Email não encontrado");
            });
        
        // Gera token de reset (válido por 1 hora)
        String resetToken = tokenService.generateResetToken(advogado.getId(), emailStr);
        
        // TODO: Enviar email com link de reset
        // emailService.enviarEmailResetSenha(advogado.getEmail().getValor(), advogado.getNome(), resetToken);
        
        log.info("Token de reset gerado para advogado ID: {} (válido por 1 hora)", advogado.getId());
        log.debug("TOKEN DE RESET (DEV): {}", resetToken); // REMOVER EM PRODUÇÃO!
        
        return new EsqueceuSenhaResult(
            "Email com instruções para reset de senha foi enviado",
            resetToken // REMOVER EM PRODUÇÃO! Deve ser enviado apenas por email
        );
    }
    
    /**
     * Resultado da solicitação de reset
     */
    public record EsqueceuSenhaResult(
        String mensagem,
        String resetToken // APENAS PARA DESENVOLVIMENTO - REMOVER EM PRODUÇÃO!
    ) {}
}


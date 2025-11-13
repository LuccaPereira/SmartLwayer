package smartLegalApi.application.auth.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.application.advogado.usecase.CriarAdvogadoUseCase;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.shared.exception.BusinessRuleException;

/**
 * Caso de uso: Registrar novo advogado no sistema
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrarAdvogadoUseCase {
    
    private final CriarAdvogadoUseCase criarAdvogadoUseCase;
    
    @Transactional
    public RegistroResult executar(RegistroRequest request) {
        log.info("Iniciando registro de novo advogado: {}", request.email());
        
        // Valida senha
        validarSenha(request.senha(), request.confirmarSenha());
        
        // Monta OAB no formato esperado (UF+numero)
        String oabCompleto = request.oabUf() + request.oabNumero();
        
        // Cria o advogado usando o use case existente
        Advogado advogadoCriado = criarAdvogadoUseCase.executar(
            request.nome(),
            oabCompleto,
            request.cpf(),
            request.email(),
            request.telefone(),
            request.senha()
        );
        
        log.info("Advogado registrado com sucesso: ID {}", advogadoCriado.getId());
        
        return new RegistroResult(
            advogadoCriado.getId(),
            advogadoCriado.getEmail().getValor(),
            advogadoCriado.getNome(),
            advogadoCriado.getOab().getValor()
        );
    }
    
    private void validarSenha(String senha, String confirmarSenha) {
        if (senha == null || senha.isBlank()) {
            throw new BusinessRuleException("Senha é obrigatória");
        }
        
        if (senha.length() < 8) {
            throw new BusinessRuleException("Senha deve ter no mínimo 8 caracteres");
        }
        
        if (!senha.equals(confirmarSenha)) {
            throw new BusinessRuleException("As senhas não coincidem");
        }
        
        // Validação de complexidade
        boolean temLetra = senha.matches(".*[a-zA-Z].*");
        boolean temNumero = senha.matches(".*\\d.*");
        
        if (!temLetra || !temNumero) {
            throw new BusinessRuleException("Senha deve conter letras e números");
        }
    }
    
    /**
     * Request para registro
     */
    public record RegistroRequest(
        String oabNumero,
        String oabUf,
        String nome,
        String cpf,
        String email,
        String telefone,
        String senha,
        String confirmarSenha
    ) {}
    
    /**
     * Resultado do registro
     */
    public record RegistroResult(
        Long id,
        String email,
        String nome,
        String oab
    ) {}
}


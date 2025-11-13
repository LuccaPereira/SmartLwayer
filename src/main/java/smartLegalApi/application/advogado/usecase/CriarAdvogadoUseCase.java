package smartLegalApi.application.advogado.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartLegalApi.domain.advogado.entity.Advogado;
import smartLegalApi.domain.advogado.exception.EmailJaCadastradoException;
import smartLegalApi.domain.advogado.exception.OABJaCadastradaException;
import smartLegalApi.domain.advogado.repository.AdvogadoRepository;
import smartLegalApi.domain.shared.valueobject.CPF;
import smartLegalApi.domain.shared.valueobject.Email;
import smartLegalApi.domain.shared.valueobject.OAB;
import smartLegalApi.domain.shared.valueobject.Telefone;
import smartLegalApi.infrastructure.security.service.PasswordEncoderService;

/**
 * Caso de Uso: Criar Novo Advogado
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CriarAdvogadoUseCase {
    
    private final AdvogadoRepository advogadoRepository;
    private final PasswordEncoderService passwordEncoder;
    
    @Transactional
    public Advogado executar(String nome, String oabStr, String cpfStr, String emailStr, String telefoneStr, String senha) {
        log.info("Iniciando criação de advogado com OAB: {}", oabStr);
        
        // Validar senha
        passwordEncoder.validarSenha(senha);
        
        // Criar Value Objects
        OAB oab = new OAB(oabStr);
        CPF cpf = new CPF(cpfStr);
        Email email = new Email(emailStr);
        Telefone telefone = telefoneStr != null && !telefoneStr.isBlank() ? new Telefone(telefoneStr) : null;
        
        // Validar unicidade
        if (advogadoRepository.existsByOAB(oab.toString())) {
            throw new OABJaCadastradaException(oab.toString());
        }
        
        if (advogadoRepository.existsByEmail(email.toString())) {
            throw new EmailJaCadastradoException(email.toString());
        }
        
        // Codificar senha
        String senhaHash = passwordEncoder.encode(senha);
        
        // Criar entidade
        Advogado advogado = new Advogado(oab, nome, cpf, email, telefone, senhaHash);
        
        // Salvar
        Advogado advogadoSalvo = advogadoRepository.save(advogado);
        
        log.info("Advogado criado com sucesso. ID: {}, OAB: {}", advogadoSalvo.getId(), advogadoSalvo.getOab());
        
        return advogadoSalvo;
    }
}


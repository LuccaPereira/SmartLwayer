package smartLegalApi.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço para codificação e validação de senhas
 */
@Service
@RequiredArgsConstructor
public class PasswordEncoderService {
    
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    /**
     * Codifica uma senha em texto plano
     */
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    /**
     * Verifica se a senha fornecida corresponde à senha codificada
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    /**
     * Valida se a senha atende aos requisitos mínimos
     */
    public void validarSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        
        if (senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");
        }
        
        if (senha.length() > 100) {
            throw new IllegalArgumentException("Senha deve ter no máximo 100 caracteres");
        }
    }
}


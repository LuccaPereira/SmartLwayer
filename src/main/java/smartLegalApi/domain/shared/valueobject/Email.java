package smartLegalApi.domain.shared.valueobject;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.DomainException;

import java.util.regex.Pattern;

/**
 * Value Object para Email
 * Garante que um email seja sempre válido quando criado
 */
@Embeddable
@Getter
@EqualsAndHashCode
public class Email {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_]+(\\.[A-Za-z0-9+_]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$"
    );
    
    private String valor;

    // Construtor protegido para JPA
    protected Email() {}

    public Email(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DomainException("Email não pode ser nulo ou vazio");
        }
        
        String emailLimpo = valor.trim().toLowerCase();
        
        if (!isValido(emailLimpo)) {
            throw new DomainException("Email inválido: " + valor);
        }
        
        this.valor = emailLimpo;
    }

    private boolean isValido(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Retorna o domínio do email
     * Ex: joao@example.com -> example.com
     */
    public String getDominio() {
        return valor.substring(valor.indexOf('@') + 1);
    }

    /**
     * Retorna a parte local do email (antes do @)
     * Ex: joao@example.com -> joao
     */
    public String getParteLocal() {
        return valor.substring(0, valor.indexOf('@'));
    }

    @Override
    public String toString() {
        return valor;
    }
}


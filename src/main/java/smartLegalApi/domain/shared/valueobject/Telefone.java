package smartLegalApi.domain.shared.valueobject;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.DomainException;

/**
 * Value Object para Telefone
 * Aceita formatos: (11) 98888-8888, 11988888888, etc
 */
@Embeddable
@Getter
@EqualsAndHashCode
public class Telefone {
    
    private String valor;

    // Construtor protegido para JPA
    protected Telefone() {}

    public Telefone(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DomainException("Telefone não pode ser nulo ou vazio");
        }
        
        String telefoneLimpo = limpar(valor);
        
        if (!isValido(telefoneLimpo)) {
            throw new DomainException("Telefone inválido: " + valor + ". Deve ter 10 ou 11 dígitos");
        }
        
        this.valor = telefoneLimpo;
    }

    private String limpar(String telefone) {
        return telefone.replaceAll("[^0-9]", "");
    }

    private boolean isValido(String telefone) {
        // Aceita telefones com 10 dígitos (fixo) ou 11 dígitos (celular)
        if (telefone.length() != 10 && telefone.length() != 11) {
            return false;
        }
        
        // Verifica se o DDD é válido (10-99)
        int ddd = Integer.parseInt(telefone.substring(0, 2));
        if (ddd < 10 || ddd > 99) {
            return false;
        }
        
        // Se for celular (11 dígitos), deve começar com 9
        if (telefone.length() == 11) {
            return telefone.charAt(2) == '9';
        }
        
        return true;
    }

    /**
     * Retorna o DDD
     * Ex: 11988888888 -> 11
     */
    public String getDDD() {
        return valor.substring(0, 2);
    }

    /**
     * Retorna o número sem DDD
     * Ex: 11988888888 -> 988888888
     */
    public String getNumero() {
        return valor.substring(2);
    }

    /**
     * Verifica se é celular (11 dígitos)
     */
    public boolean isCelular() {
        return valor.length() == 11;
    }

    /**
     * Retorna o telefone formatado
     * Ex: 11988888888 -> (11) 98888-8888
     * Ex: 1133334444 -> (11) 3333-4444
     */
    public String formatado() {
        if (isCelular()) {
            return String.format("(%s) %s-%s",
                getDDD(),
                valor.substring(2, 7),
                valor.substring(7)
            );
        } else {
            return String.format("(%s) %s-%s",
                getDDD(),
                valor.substring(2, 6),
                valor.substring(6)
            );
        }
    }

    @Override
    public String toString() {
        return valor;
    }
}


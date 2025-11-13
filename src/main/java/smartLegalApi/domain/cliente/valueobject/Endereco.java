package smartLegalApi.domain.cliente.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.DomainException;

/**
 * Value Object para Endereço
 */
@Getter
@EqualsAndHashCode
public class Endereco {
    
    private String valor;

    // Construtor protegido para JPA
    protected Endereco() {}

    public Endereco(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DomainException("Endereço não pode ser vazio");
        }
        
        if (valor.length() > 255) {
            throw new DomainException("Endereço deve ter no máximo 255 caracteres");
        }
        
        this.valor = valor.trim();
    }

    @Override
    public String toString() {
        return valor;
    }
}


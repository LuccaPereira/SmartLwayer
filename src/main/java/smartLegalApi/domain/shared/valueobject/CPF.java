package smartLegalApi.domain.shared.valueobject;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.DomainException;

/**
 * Value Object para CPF
 * Garante que um CPF seja sempre válido quando criado
 */
@Embeddable
@Getter
@EqualsAndHashCode
public class CPF {
    
    private String valor;

    // Construtor protegido para JPA
    protected CPF() {}

    public CPF(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DomainException("CPF não pode ser nulo ou vazio");
        }
        
        String cpfLimpo = limpar(valor);
        
        if (!isValido(cpfLimpo)) {
            throw new DomainException("CPF inválido: " + valor);
        }
        
        this.valor = cpfLimpo;
    }

    private String limpar(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

    private boolean isValido(String cpf) {
        if (cpf.length() != 11) {
            return false;
        }
        
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        // Validação do primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digito1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        // Validação do segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int digito2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        return Character.getNumericValue(cpf.charAt(9)) == digito1 &&
               Character.getNumericValue(cpf.charAt(10)) == digito2;
    }

    /**
     * Retorna o CPF formatado: 000.000.000-00
     */
    public String formatado() {
        return String.format("%s.%s.%s-%s",
            valor.substring(0, 3),
            valor.substring(3, 6),
            valor.substring(6, 9),
            valor.substring(9, 11)
        );
    }

    @Override
    public String toString() {
        return valor;
    }
}


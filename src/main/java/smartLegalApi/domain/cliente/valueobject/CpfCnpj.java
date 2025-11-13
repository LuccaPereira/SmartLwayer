package smartLegalApi.domain.cliente.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.DomainException;

/**
 * Value Object para CPF ou CNPJ
 * Aceita tanto pessoa física quanto jurídica
 */
@Getter
@EqualsAndHashCode
public class CpfCnpj {
    
    private String valor;
    private TipoPessoa tipo;

    public enum TipoPessoa {
        FISICA,    // CPF
        JURIDICA   // CNPJ
    }

    // Construtor protegido para JPA
    protected CpfCnpj() {}

    public CpfCnpj(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DomainException("CPF/CNPJ não pode ser vazio");
        }
        
        String valorLimpo = limpar(valor);
        
        if (valorLimpo.length() == 11) {
            if (!isValidCPF(valorLimpo)) {
                throw new DomainException("CPF inválido: " + valor);
            }
            this.tipo = TipoPessoa.FISICA;
        } else if (valorLimpo.length() == 14) {
            if (!isValidCNPJ(valorLimpo)) {
                throw new DomainException("CNPJ inválido: " + valor);
            }
            this.tipo = TipoPessoa.JURIDICA;
        } else {
            throw new DomainException("CPF/CNPJ inválido. Deve ter 11 (CPF) ou 14 (CNPJ) dígitos");
        }
        
        this.valor = valorLimpo;
    }

    private String limpar(String valor) {
        return valor.replaceAll("[^0-9]", "");
    }

    private boolean isValidCPF(String cpf) {
        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        // Validação dos dígitos verificadores
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digito1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int digito2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        return Character.getNumericValue(cpf.charAt(9)) == digito1 &&
               Character.getNumericValue(cpf.charAt(10)) == digito2;
    }

    private boolean isValidCNPJ(String cnpj) {
        // Primeiro dígito verificador
        int soma = 0;
        int peso = 2;
        for (int i = 11; i >= 0; i--) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
            peso = peso == 9 ? 2 : peso + 1;
        }
        int digito1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        // Segundo dígito verificador
        soma = 0;
        peso = 2;
        for (int i = 12; i >= 0; i--) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
            peso = peso == 9 ? 2 : peso + 1;
        }
        int digito2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        return Character.getNumericValue(cnpj.charAt(12)) == digito1 &&
               Character.getNumericValue(cnpj.charAt(13)) == digito2;
    }

    /**
     * Retorna o CPF/CNPJ formatado
     */
    public String formatado() {
        if (tipo == TipoPessoa.FISICA) {
            // CPF: 000.000.000-00
            return String.format("%s.%s.%s-%s",
                valor.substring(0, 3),
                valor.substring(3, 6),
                valor.substring(6, 9),
                valor.substring(9, 11)
            );
        } else {
            // CNPJ: 00.000.000/0000-00
            return String.format("%s.%s.%s/%s-%s",
                valor.substring(0, 2),
                valor.substring(2, 5),
                valor.substring(5, 8),
                valor.substring(8, 12),
                valor.substring(12, 14)
            );
        }
    }

    public boolean isPessoaFisica() {
        return tipo == TipoPessoa.FISICA;
    }

    public boolean isPessoaJuridica() {
        return tipo == TipoPessoa.JURIDICA;
    }

    @Override
    public String toString() {
        return valor;
    }
}


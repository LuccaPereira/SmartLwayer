package smartLegalApi.domain.shared.valueobject;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.DomainException;

import java.util.regex.Pattern;

/**
 * Value Object para OAB (Ordem dos Advogados do Brasil)
 * Formato esperado: UF123456 ou UF 123456
 */
@Embeddable
@Getter
@EqualsAndHashCode
public class OAB {
    
    // Padrão: 2 letras (UF) + 6 dígitos
    private static final Pattern OAB_PATTERN = Pattern.compile("^[A-Z]{2}\\d{6}$");
    
    private static final String[] UFS_VALIDAS = {
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
        "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
        "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    };
    
    private String valor;

    // Construtor protegido para JPA
    protected OAB() {}

    public OAB(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DomainException("OAB não pode ser nulo ou vazio");
        }
        
        String oabLimpo = limpar(valor);
        
        if (!isValido(oabLimpo)) {
            throw new DomainException("OAB inválido: " + valor + ". Formato esperado: UF123456");
        }
        
        this.valor = oabLimpo;
    }

    private String limpar(String oab) {
        return oab.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
    }

    private boolean isValido(String oab) {
        if (!OAB_PATTERN.matcher(oab).matches()) {
            return false;
        }
        
        // Valida se a UF é válida
        String uf = oab.substring(0, 2);
        for (String ufValida : UFS_VALIDAS) {
            if (ufValida.equals(uf)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Retorna a UF da OAB
     * Ex: SP123456 -> SP
     */
    public String getUF() {
        return valor.substring(0, 2);
    }

    /**
     * Retorna o número da OAB
     * Ex: SP123456 -> 123456
     */
    public String getNumero() {
        return valor.substring(2);
    }

    /**
     * Retorna a OAB formatada: UF 123456
     */
    public String formatado() {
        return getUF() + " " + getNumero();
    }

    @Override
    public String toString() {
        return valor;
    }
}


package smartLegalApi.domain.processo.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import smartLegalApi.domain.shared.exception.DomainException;

import java.util.regex.Pattern;

/**
 * Value Object para Número do Processo (padrão CNJ)
 * Formato: NNNNNNN-DD.AAAA.J.TR.OOOO
 */
@Getter
@EqualsAndHashCode
public class NumeroProcesso {
    
    // Padrão CNJ: 7 dígitos - 2 dígitos . 4 dígitos . 1 dígito . 2 dígitos . 4 dígitos
    private static final Pattern NUMERO_PROCESSO_PATTERN = Pattern.compile("^\\d{7}-\\d{2}\\.\\d{4}\\.\\d\\.\\d{2}\\.\\d{4}$");
    
    private String valor;

    protected NumeroProcesso() {}

    public NumeroProcesso(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new DomainException("Número do processo não pode ser vazio");
        }
        
        String numeroLimpo = valor.trim();
        
        if (!isValido(numeroLimpo)) {
            throw new DomainException("Número do processo inválido. Formato esperado: NNNNNNN-DD.AAAA.J.TR.OOOO");
        }
        
        this.valor = numeroLimpo;
    }

    private boolean isValido(String numero) {
        return NUMERO_PROCESSO_PATTERN.matcher(numero).matches();
    }

    /**
     * Retorna apenas os números do processo (sem formatação)
     */
    public String apenasNumeros() {
        return valor.replaceAll("[^0-9]", "");
    }

    @Override
    public String toString() {
        return valor;
    }
}


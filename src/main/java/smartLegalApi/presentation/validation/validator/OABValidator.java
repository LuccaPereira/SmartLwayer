package smartLegalApi.presentation.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import smartLegalApi.presentation.validation.annotation.ValidOAB;

import java.util.regex.Pattern;

/**
 * Validador de OAB
 */
public class OABValidator implements ConstraintValidator<ValidOAB, String> {
    
    private static final Pattern OAB_PATTERN = Pattern.compile("^[A-Z]{2}\\d{6}$");
    
    private static final String[] UFS_VALIDAS = {
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
        "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
        "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    };
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        
        // Limpar e converter para maiúsculo
        String oabLimpo = value.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        
        // Validar padrão
        if (!OAB_PATTERN.matcher(oabLimpo).matches()) {
            return false;
        }
        
        // Validar UF
        String uf = oabLimpo.substring(0, 2);
        for (String ufValida : UFS_VALIDAS) {
            if (ufValida.equals(uf)) {
                return true;
            }
        }
        
        return false;
    }
}


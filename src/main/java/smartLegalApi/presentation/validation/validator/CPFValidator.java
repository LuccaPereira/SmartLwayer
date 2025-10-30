package smartLegalApi.presentation.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import smartLegalApi.presentation.validation.annotation.ValidCPF;

/**
 * Validador de CPF
 */
public class CPFValidator implements ConstraintValidator<ValidCPF, String> {
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        
        // Limpar
        String cpf = value.replaceAll("[^0-9]", "");
        
        // Validar tamanho
        if (cpf.length() != 11) {
            return false;
        }
        
        // Validar se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        
        // Validar dígitos verificadores
        return validarDigitos(cpf);
    }
    
    private boolean validarDigitos(String cpf) {
        // Primeiro dígito
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digito1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        // Segundo dígito
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int digito2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        
        return Character.getNumericValue(cpf.charAt(9)) == digito1 &&
               Character.getNumericValue(cpf.charAt(10)) == digito2;
    }
}


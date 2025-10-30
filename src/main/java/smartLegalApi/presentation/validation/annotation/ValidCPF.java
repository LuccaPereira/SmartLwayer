package smartLegalApi.presentation.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import smartLegalApi.presentation.validation.validator.CPFValidator;

import java.lang.annotation.*;

/**
 * Anotação para validar CPF
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CPFValidator.class)
@Documented
public @interface ValidCPF {
    
    String message() default "CPF inválido";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}


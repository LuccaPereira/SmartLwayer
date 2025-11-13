package smartLegalApi.presentation.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import smartLegalApi.presentation.validation.validator.OABValidator;

import java.lang.annotation.*;

/**
 * Anotação para validar OAB
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OABValidator.class)
@Documented
public @interface ValidOAB {
    
    String message() default "OAB inválida";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}


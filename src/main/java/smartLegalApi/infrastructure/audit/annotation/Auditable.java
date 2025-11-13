package smartLegalApi.infrastructure.audit.annotation;

import smartLegalApi.domain.audit.valueobject.TipoOperacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para marcar métodos que devem ser auditados
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    
    /**
     * Tipo de operação sendo realizada
     */
    TipoOperacao operacao();
    
    /**
     * Nome da entidade sendo manipulada
     */
    String entidade();
    
    /**
     * Descrição da operação (opcional)
     */
    String descricao() default "";
}


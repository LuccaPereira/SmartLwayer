package smartLegalApi.domain.advogado.exception;

import smartLegalApi.domain.shared.exception.BusinessRuleException;

/**
 * Exception lançada quando tenta cadastrar uma OAB já existente
 */
public class OABJaCadastradaException extends BusinessRuleException {
    
    public OABJaCadastradaException(String oab) {
        super(String.format("OAB %s já está cadastrada no sistema", oab));
    }
}


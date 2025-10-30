package smartLegalApi.domain.advogado.exception;

import smartLegalApi.domain.shared.exception.BusinessRuleException;

/**
 * Exception lançada quando tenta cadastrar um email já existente
 */
public class EmailJaCadastradoException extends BusinessRuleException {
    
    public EmailJaCadastradoException(String email) {
        super(String.format("Email %s já está cadastrado no sistema", email));
    }
}


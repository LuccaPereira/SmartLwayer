package smartLegalApi.domain.cliente.exception;

import smartLegalApi.domain.shared.exception.BusinessRuleException;

/**
 * Exception lançada quando tenta cadastrar um CPF/CNPJ já existente
 */
public class CpfCnpjJaCadastradoException extends BusinessRuleException {
    
    public CpfCnpjJaCadastradoException(String cpfCnpj) {
        super(String.format("CPF/CNPJ %s já está cadastrado no sistema", cpfCnpj));
    }
}


package smartLegalApi.domain.processo.exception;

import smartLegalApi.domain.shared.exception.BusinessRuleException;

public class NumeroProcessoJaCadastradoException extends BusinessRuleException {
    
    public NumeroProcessoJaCadastradoException(String numeroProcesso) {
        super(String.format("Número de processo %s já está cadastrado", numeroProcesso));
    }
}


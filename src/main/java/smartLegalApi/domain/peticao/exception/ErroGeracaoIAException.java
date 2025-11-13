package smartLegalApi.domain.peticao.exception;

import smartLegalApi.domain.shared.exception.DomainException;

/**
 * Exception para erros na geração de petição com IA
 */
public class ErroGeracaoIAException extends DomainException {
    
    public ErroGeracaoIAException(String mensagem) {
        super(mensagem);
    }
    
    public ErroGeracaoIAException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}


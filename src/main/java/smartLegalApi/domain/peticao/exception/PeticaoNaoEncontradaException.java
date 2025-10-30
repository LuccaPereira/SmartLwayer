package smartLegalApi.domain.peticao.exception;

import smartLegalApi.domain.shared.exception.NotFoundException;

/**
 * Exception para petição não encontrada
 */
public class PeticaoNaoEncontradaException extends NotFoundException {
    
    public PeticaoNaoEncontradaException(Long id) {
        super("Petição não encontrada com ID: " + id);
    }
    
    public PeticaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}


package smartLegalApi.domain.advogado.exception;

import smartLegalApi.domain.shared.exception.NotFoundException;

/**
 * Exception lançada quando um advogado não é encontrado
 */
public class AdvogadoNaoEncontradoException extends NotFoundException {
    
    public AdvogadoNaoEncontradoException(Long id) {
        super("Advogado", id);
    }
    
    public AdvogadoNaoEncontradoException(String oab) {
        super(String.format("Advogado com OAB %s não encontrado", oab));
    }
}


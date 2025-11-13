package smartLegalApi.domain.cliente.exception;

import smartLegalApi.domain.shared.exception.NotFoundException;

/**
 * Exception lançada quando um cliente não é encontrado
 */
public class ClienteNaoEncontradoException extends NotFoundException {
    
    public ClienteNaoEncontradoException(Long id) {
        super("Cliente", id);
    }
    
    public ClienteNaoEncontradoException(String cpfCnpj) {
        super(String.format("Cliente com CPF/CNPJ %s não encontrado", cpfCnpj));
    }
}


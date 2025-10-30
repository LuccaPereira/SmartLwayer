package smartLegalApi.domain.shared.exception;

/**
 * Exception lançada quando uma entidade não é encontrada
 */
public class NotFoundException extends DomainException {
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String entidade, Object id) {
        super(String.format("%s com ID %s não encontrado(a)", entidade, id));
    }
}


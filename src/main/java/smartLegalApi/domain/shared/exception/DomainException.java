package smartLegalApi.domain.shared.exception;

/**
 * Exception base para todas as exceções de domínio
 * Representa violações de regras de negócio
 */
public class DomainException extends RuntimeException {
    
    public DomainException(String message) {
        super(message);
    }
    
    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}


package smartLegalApi.domain.shared.exception;

/**
 * Exception lançada quando uma regra de negócio é violada
 */
public class BusinessRuleException extends DomainException {
    
    public BusinessRuleException(String message) {
        super(message);
    }
    
    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}


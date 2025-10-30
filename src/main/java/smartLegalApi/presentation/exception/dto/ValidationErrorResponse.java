package smartLegalApi.presentation.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para respostas de erro de validação
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    
    private int status;
    
    private String error;
    
    private String message;
    
    private String path;
    
    @Builder.Default
    private List<FieldError> errors = new ArrayList<>();
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldError {
        private String field;
        private String message;
        private Object rejectedValue;
    }
    
    public void addFieldError(String field, String message, Object rejectedValue) {
        errors.add(new FieldError(field, message, rejectedValue));
    }
    
    public static ValidationErrorResponse of(int status, String error, String message, String path) {
        return ValidationErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status)
            .error(error)
            .message(message)
            .path(path)
            .errors(new ArrayList<>())
            .build();
    }
}


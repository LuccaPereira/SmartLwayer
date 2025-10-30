package smartLegalApi.application.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta de validação de token")
public class TokenValidationResponse {
    
    @Schema(description = "Indica se o token é válido", example = "true")
    private boolean valid;
    
    @Schema(description = "ID do usuário", example = "1")
    private Long userId;
    
    @Schema(description = "Email do usuário", example = "advogado@smartlegal.com")
    private String email;
    
    @Schema(description = "Role do usuário", example = "ADVOGADO")
    private String role;
}


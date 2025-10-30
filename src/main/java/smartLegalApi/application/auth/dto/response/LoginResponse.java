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
@Schema(description = "Resposta de login com tokens")
public class LoginResponse {
    
    @Schema(description = "Token de acesso JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;
    
    @Schema(description = "Refresh token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;
    
    @Schema(description = "Tipo do token", example = "Bearer")
    private String tokenType;
    
    @Schema(description = "Tempo de expiração em milissegundos", example = "86400000")
    private Long expiresIn;
    
    @Schema(description = "ID do usuário", example = "1")
    private Long userId;
    
    @Schema(description = "Email do usuário", example = "advogado@smartlegal.com")
    private String email;
    
    @Schema(description = "Nome do usuário", example = "João Silva")
    private String nome;
    
    @Schema(description = "Role do usuário", example = "ADVOGADO")
    private String role;
}


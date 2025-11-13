package smartLegalApi.application.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de Response: Informações do usuário autenticado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informações do usuário autenticado")
public class UserInfoResponse {
    
    @Schema(description = "ID do usuário", example = "1")
    private Long id;
    
    @Schema(description = "Email do usuário", example = "joao@example.com")
    private String email;
    
    @Schema(description = "Nome do usuário", example = "João da Silva")
    private String nome;
    
    @Schema(description = "Role/perfil do usuário", example = "ROLE_ADVOGADO")
    private String role;
}


package smartLegalApi.application.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de Response: Resultado do registro
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resultado do registro de advogado")
public class RegistroResponse {
    
    @Schema(description = "ID do advogado criado", example = "1")
    private Long id;
    
    @Schema(description = "Email do advogado", example = "joao@example.com")
    private String email;
    
    @Schema(description = "Nome do advogado", example = "João da Silva")
    private String nome;
    
    @Schema(description = "Número da OAB", example = "SP123456")
    private String oab;
    
    @Schema(description = "Mensagem de sucesso")
    private String mensagem;
}


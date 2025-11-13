package smartLegalApi.application.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de Request: Alterar senha (usuário autenticado)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para alteração de senha")
public class AlterarSenhaRequest {
    
    @NotBlank(message = "Senha atual é obrigatória")
    @Schema(description = "Senha atual", example = "SenhaAntiga123")
    private String senhaAtual;
    
    @NotBlank(message = "Nova senha é obrigatória")
    @Size(min = 8, message = "Nova senha deve ter no mínimo 8 caracteres")
    @Schema(description = "Nova senha (mínimo 8 caracteres, letras e números)", example = "SenhaNova123")
    private String novaSenha;
    
    @NotBlank(message = "Confirmação de senha é obrigatória")
    @Schema(description = "Confirmação da nova senha", example = "SenhaNova123")
    private String confirmarNovaSenha;
}


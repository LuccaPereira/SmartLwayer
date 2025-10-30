package smartLegalApi.application.advogado.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de requisição para atualizar advogado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para atualizar um advogado")
public class AtualizarAdvogadoRequest {
    
    @Schema(description = "Nome completo do advogado", example = "Dr. João Silva Júnior")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @Schema(description = "Email do advogado", example = "joao.junior@example.com")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;
    
    @Schema(description = "Telefone do advogado (apenas números)", example = "11999999999")
    @Size(min = 10, max = 11, message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;
}


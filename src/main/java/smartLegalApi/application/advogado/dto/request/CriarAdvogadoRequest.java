package smartLegalApi.application.advogado.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de requisição para criar advogado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para criar um novo advogado")
public class CriarAdvogadoRequest {
    
    @Schema(description = "Nome completo do advogado", example = "Dr. João Silva")
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    
    @Schema(description = "Número da OAB", example = "SP123456")
    @NotBlank(message = "OAB é obrigatória")
    @Size(min = 8, max = 8, message = "OAB deve ter 8 caracteres (UF + 6 dígitos)")
    private String oab;
    
    @Schema(description = "CPF do advogado (apenas números)", example = "12345678901")
    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    private String cpf;
    
    @Schema(description = "Email do advogado", example = "joao.silva@example.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;
    
    @Schema(description = "Telefone do advogado (apenas números)", example = "11988888888")
    @Size(min = 10, max = 11, message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;
    
    @Schema(description = "Senha do advogado", example = "senha123")
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
    private String senha;
}


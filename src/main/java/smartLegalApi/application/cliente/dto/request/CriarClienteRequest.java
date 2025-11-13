package smartLegalApi.application.cliente.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de requisição para criar cliente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para criar um novo cliente")
public class CriarClienteRequest {
    
    @Schema(description = "Nome completo do cliente", example = "Maria Santos Silva")
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nomeCompleto;
    
    @Schema(description = "CPF (11 dígitos) ou CNPJ (14 dígitos) apenas números", example = "12345678901")
    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Size(min = 11, max = 14, message = "CPF deve ter 11 dígitos e CNPJ 14 dígitos")
    private String cpfCnpj;
    
    @Schema(description = "Email do cliente", example = "maria.santos@example.com")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;
    
    @Schema(description = "Telefone do cliente (apenas números)", example = "11977777777")
    @Size(min = 10, max = 11, message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;
    
    @Schema(description = "Endereço completo", example = "Rua das Flores, 123 - Centro - São Paulo/SP - CEP 01234-567")
    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String endereco;
}


package smartLegalApi.application.cliente.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de requisição para atualizar cliente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para atualizar um cliente")
public class AtualizarClienteRequest {
    
    @Schema(description = "Nome completo do cliente", example = "Maria Santos Silva Oliveira")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nomeCompleto;
    
    @Schema(description = "Email do cliente", example = "maria.oliveira@example.com")
    @Email(message = "Email inválido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;
    
    @Schema(description = "Telefone do cliente (apenas números)", example = "11988888888")
    @Size(min = 10, max = 11, message = "Telefone deve ter 10 ou 11 dígitos")
    private String telefone;
    
    @Schema(description = "Endereço completo", example = "Av. Paulista, 1000 - Bela Vista - São Paulo/SP")
    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String endereco;
}


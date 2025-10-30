package smartLegalApi.application.processo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para criar processo")
public class CriarProcessoRequest {
    
    @NotBlank(message = "Número do processo é obrigatório")
    @Schema(description = "Número do processo (formato CNJ)", example = "1234567-89.2024.8.21.0001")
    private String numeroProcesso;
    
    @NotBlank(message = "Título é obrigatório")
    @Schema(description = "Título do processo", example = "Ação de Cobrança")
    private String titulo;
    
    @Schema(description = "Descrição detalhada do processo")
    private String descricao;
    
    @NotNull(message = "ID do advogado é obrigatório")
    @Schema(description = "ID do advogado responsável", example = "1")
    private Long idAdvogado;
    
    @NotNull(message = "ID do cliente é obrigatório")
    @Schema(description = "ID do cliente", example = "1")
    private Long idCliente;
}


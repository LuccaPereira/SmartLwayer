package smartLegalApi.application.processo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para criar andamento")
public class CriarAndamentoRequest {
    
    @NotNull(message = "Data do andamento é obrigatória")
    @Schema(description = "Data e hora do andamento", example = "2024-01-15T14:30:00")
    private LocalDateTime dataAndamento;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Schema(description = "Descrição do andamento", example = "Juntada de documentos")
    private String descricao;
    
    @Schema(description = "Tipo do andamento", example = "AUDIENCIA")
    private String tipo;
}


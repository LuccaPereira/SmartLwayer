package smartLegalApi.application.processo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para atualizar processo")
public class AtualizarProcessoRequest {
    
    @NotBlank(message = "Título é obrigatório")
    @Schema(description = "Título do processo", example = "Ação de Cobrança - Atualizada")
    private String titulo;
    
    @Schema(description = "Descrição detalhada do processo")
    private String descricao;
}


package smartLegalApi.application.peticao.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartLegalApi.domain.peticao.valueobject.TipoPeticao;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para criar petição")
public class CriarPeticaoRequest {
    
    @NotNull(message = "ID do processo é obrigatório")
    @Schema(description = "ID do processo", example = "1")
    private Long idProcesso;
    
    @NotNull(message = "ID do advogado é obrigatório")
    @Schema(description = "ID do advogado responsável", example = "1")
    private Long idAdvogado;
    
    @NotNull(message = "Tipo da petição é obrigatório")
    @Schema(description = "Tipo da petição", example = "INICIAL")
    private TipoPeticao tipo;
    
    @NotBlank(message = "Título é obrigatório")
    @Schema(description = "Título da petição", example = "Ação de Cobrança - Petição Inicial")
    private String titulo;
}

